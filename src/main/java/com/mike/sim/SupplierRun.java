package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

/*
 */
public class SupplierRun {

    private static final String TAG = SupplierRun.class.getSimpleName();
    private static int nextId = 0;

    private final int id;
    private List<Order> orders = new ArrayList<>();
    private List<Stop> stops = new ArrayList<Stop>();
    private AnnealData annealer;

    public SupplierRun(ConsumerRun consumerRun) {
        id = nextId++;
        orders = sim.getSupplierOrders(consumerRun);

        createRoute();
    }

    private void createRoute() {
        annealer = new AnnealData();
//        annealer.setupDepotRun(orders);
        annealer.anneal();
    }

    // iterate the route, accumulating the cost
    public void deliver() {
        StringBuilder sb = new StringBuilder();
//        Location loc =
        double distance = 0;
        for (Order order : orders) {
            distance += deliver(order);
//            sb.append(String.format("(%03d %04d) ", order.getDepot().getId(), order.getId()));
        }

        Log.d(TAG, String.format("%4d %s %5.1f", id, sb.toString(), distance));
    }

    private static double deliver(Order order) {
        double distance = 0;
//        distance += order.getSupplier().distance(order.getDepot());
        return distance;
    }

}
