package com.mike.sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 2/18/2017.
 */
public class Route {

    static protected boolean test = true;

    private static final String TAG = Route.class.getSimpleName();
    protected static Random random;
    protected List<Stop> stops = new ArrayList<>();
    private RouteErrors errors;

    public List<Stop> getStops() {
        return stops;
    }

    protected Metrics metrics;

    public Route(AnnealData data, Random random) {
        this.stops = data.getStops();
        metrics = new Metrics(stops);

        this.random = random;

        isDeliverable(stops);
    }

    public Route(Route route) {
        this.stops = new ArrayList<>(route.stops);
        metrics = new Metrics(stops);

        isDeliverable(stops);
    }

//    /**
//     * @param pickup or delivery
//     * @param user
//     * @return true if route contains the given user in the given role
//     */
//    public boolean containsStop(boolean pickup, User user) {
//        for (Stop stop : getStops()) {
//            if (pickup) {
//                if (stop.isPickup()) {
//                    if (((Stop) stop).getUser().getUsername().equals(user.getUsername()))
//                        return true;
//                }
//            } else {
//                if (stop.isDelivery()) {
//                    if (((Stop) stop).getUser().getUsername().equals(user.getUsername()))
//                        return true;
//                }
//            }
//        }
//        return false;
//    }

    public double getCost() {
        return metrics.getCost();
    }
    public Metrics getMetrics() {
        return metrics;
    }

    public int size() {
        return stops.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double d = getMetrics().getCost();
        String ds = String.format("%.1f, ", d);
        sb.append(ds);
        stops.forEach(stop -> sb.append(stop.toString()).append(", "));

        return sb.toString();
    }

    public boolean permute() {
        return permute(stops);
    }

    /** change the route somehow so the annealer
     * has permutations to work with
     */
    private boolean permute(List<Stop> stops) {
        if (stops.size() < 2) {
            Log.d(TAG, "Route has less than 2 stops");
            return false;
        }

        // get two different stops that can be swapped

        int iterations = 0;
        boolean valid = true;
        int ai;
        int bi;
        Stop a;
        Stop b;
        do {
            ai = getMovable(stops);
            bi = getAnotherMovable(stops, ai);

            a = stops.get(ai);
            b = stops.get(bi);

            valid = true;
            if (a.isDelivery())
                valid &= canMoveDelivery(stops, ai, bi);
            else
                valid &= canMovePickup (stops, ai, bi);

            if (valid) {
                if (b.isPickup())
                    valid &= canMovePickup(stops, bi, ai);
                else
                    valid &= canMoveDelivery(stops, bi, ai);
            }

            if (++iterations > 500) {
                Log.d(TAG, "Route permutation is stuck");
                return false; //"Route permutation is stuck";
            }

        } while (! valid);

        // swap them
//        Log.d(TAG, String.format("swap %s and %s", a.toString(), b.toString()));

        stops.set(bi, a);
        stops.set(ai, b);

        metrics.needCompute();
        return true;
    }

    // is is ok to move a pick from one place to another
    static private boolean canMovePickup(List<Stop> stops, int fromI, int toI) {

        // are all the items in the 'from' pickup dropped
        // after the 'to' location, simplest just count
        // the number of 'from' items dropped after 'to'

        Stop from = stops.get(fromI);
        Stop to = stops.get(toI);

        int count = 0;
        for (int i = toI + 1; i < stops.size(); ++i){
            Stop si = stops.get(i);
            if (si.isDelivery()) {
                for (Order order : si.getOrders()) {
                    for (Order otherOrder : from.getOrders()) {
                        if (otherOrder.equals(order))
                            count++;
                    }
                }
            }
        }

        return count == from.getOrders().size();
    }

    // is it ok to move a delivery from one place to another
    static private boolean canMoveDelivery(List<Stop> stops, int fromI, int toI) {
        // are all items in the 'from' drop picked up
        // before the 'to' location

        Stop to = stops.get(fromI);

        int count = 0;
        for (int i = 0; i < toI; ++i){
            Stop pick = stops.get(i);
            if (pick.isPickup()) {
                for (Order pickOrder : pick.getOrders()) {
                    for (Order order : to.getOrders()) {
                        if (order.equals(pickOrder))
                            count++;
                    }
                }
            }
        }

        return count == to.getOrders().size();
    }

    static private int getMovable (List<Stop> stops) {
        int i = random.nextInt(stops.size());
        while (stops.get(i).isUnrouteable()) {
            i = random.nextInt(stops.size());
        }
        return i;
    }

    static private int getAnotherMovable(List<Stop> stops, int j) {
        int i = random.nextInt(stops.size());
        while (    (stops.get(i).isUnrouteable())
                || (i == j)) {
            i = random.nextInt(stops.size());
        }
        return i;
    }

    // verify that the route is deliverable, accumulate
    // the pickups, do the deliveries, make sure we have
    // what we are delivering and nothing left at the end
    public void isDeliverable(List<Stop> stops) {
        errors = new RouteErrors();
        List<Order> inTruck = new ArrayList<>();
        for(Stop stop : stops) {
            if (stop.isPickup()) {
                inTruck.addAll(stop.getOrders());
            }
            else if (stop.isDelivery()) {
                for (Order order : stop.getOrders()) {
                    if ( ! inTruck.remove(order)) {
                        errors.add(new RouteError(RouteError.Type.DidNotPickFor, order.getConsumer()));
                    }
                }
            }
        }

        if (inTruck.size() != 0) {
            inTruck.forEach(order ->
                errors.add(new RouteError(RouteError.Type.DidNotDropTo, order.getConsumer())));
        }
    }

    public RouteErrors getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return errors.hasErrors();
    }
}
