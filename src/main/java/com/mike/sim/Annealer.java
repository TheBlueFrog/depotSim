package com.mike.sim;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by mike on 2/5/2017.
 *
 */
public class Annealer {

    private static final String TAG = Annealer.class.getSimpleName();

    private boolean debug = false;
    private final AnnealData data;

    public Annealer(AnnealData data)  {
        this.data = data;

        route = new Route(data, data.getRandom()); // initial route, we assume it is valid
    }

    private Route route;

    public Route getRoute() {
        return route;
    }

    private int numDeliveries = 0;

    public Random getRandom() {
        return data.getRandom();
    }

    // Calculate the acceptance probability, like golf, lower score is better
    private double acceptanceProbability(double bestScore, double newScore, double temperature) {

        if (newScore < bestScore) {
            return 1.0; // accept for certain
        }

        // accept with some probability
        double p = Math.exp(-(newScore - bestScore) / temperature);
        return p;
    }

    public Route anneal() {

        if (3 > data.getStops().size()) {
            Log.d(TAG, "No stops, nothing to optimize");
            return route;
        }

        if (data.debug())
            Log.d(TAG, String.format("Initial solution cost: %.1f", route.getMetrics().getCost()));

        double temperature = 10000;
//        double coolingRate = 0.003;
        double coolingRate = 0.0029;
        int iterations = 0;

        // Set as current best
        Route best = new Route(route);
        if (data.debug())
            Log.d(TAG, "Initial route: " + best.toString());

        // iterate until system has cooled
        while (temperature > 1) {
            Route next = new Route(route);

            if ( ! next.permute())
                return next;
            if (next.hasErrors())
                return best;

            double p = acceptanceProbability(
                    route.getMetrics().getCost(),
                    next.getMetrics().getCost(),
                    temperature);

            if (p > getRandom().nextDouble()) {
                route = new Route(next);
            }

            if (route.getMetrics().getCost() < best.getMetrics().getCost()) {

                if (debug)
                    Log.d(TAG, "new best " + route.toString());

                best = new Route(route);
            }

            // Cool system
            temperature *= (1.0 - coolingRate);

            if (data.debug() && ((iterations % 500) == 0))
                Log.d(TAG, String.format("%4d, %.1f %s",
                        iterations,
                        (best.getMetrics().getCost() / 1000.0),
                        best.toString())); // score is length of route in meters

            iterations++;
        }

        if (data.debug()) {
            Log.d(TAG, "Iterations: " + iterations);
            Log.d(TAG, String.format("Best solution cost: %.1f", best.getMetrics().getCost()));
            Log.d(TAG, "Route: " + best.toString());
        }

        // verify both ends are still non-routeable
        if ( ! ( ! best.getStops().get(0).isRouteable()) &&
               ( ! best.getStops().get(data.getStops().size()-1).isRouteable())) {
            Log.d(TAG, "Oops");
        }

        // verify we never visit one place more than once
        {
            // collapse runs into a single stop
            ArrayList<Stop> x = new ArrayList<>(best.getStops());
            for(int i = 1; i < x.size(); ) {
                if (x.get(i-1).distance(x.get(i)) < 0.1) {
                    x.remove(i);
                }
                else
                    ++i;
            }


            boolean[] visited = new boolean[x.size()];
            Arrays.fill(visited, false);
            for(int i = 0; i < x.size(); ++i) {
                for(int j = 0; j < i; ++j) {
                    if (x.get(i).distance(x.get(j)) < 0.1)
                        Log.d(TAG, "oops");
                }
            }
        }
        return best;
    }
}

