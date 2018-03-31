package com.mike.sim;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
 * batch sizes have a lower limit of one order,
 * the number of stops in a batch is an upper limit
 * the total length of the route to deliver is an upper limit
 */
public class Batch {

    private static final String TAG = Batch.class.getSimpleName();
    private static int nextId = 0;

    private final int id;
    private List<Order> orders = new ArrayList<>();
    private List<Stop> stops = new ArrayList<Stop>();
    private AnnealData annealer;

    /** given open orders carve off a batch */
    public Batch(List<Order> orders, int maxOrders) {
        id = nextId++;

        while((orders.size() > 0) && (this.orders.size() < maxOrders)) {
            Order order = orders.remove(0);
            this.orders.add(order);
        }

        createRoute();
    }

    private void createRoute() {

        annealer = new AnnealData(orders);
        annealer.anneal();
    }

    // iterate the route, accumulating the cost
    public void deliver() {
        StringBuilder sb = new StringBuilder();
//        Location loc =
        double distance = 0;
        for (Order order : orders) {
            distance += deliver(order);
            sb.append(String.format("(%03d %04d) ", order.getConsumer().getId(), order.getId()));
        }

        Log.d(TAG, String.format("%4d %s %5.1f", id, sb.toString(), distance));
    }

    private static double deliver(Order order) {
        double distance = 0;
        distance += order.getSupplier().distance(order.getConsumer());
        return distance;
    }

}
