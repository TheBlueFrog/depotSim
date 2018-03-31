package com.mike.sim;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/*
    goal is to simulate the depot -> consumer system

    assume a perfect supply inputs to the depot, e.g. anything
    that is needed is there

    ignore aging of items in the depot since they will not
    appear too soon.

    depot -> consumer runs are made based on an optimization by
    the driver, this balances cost of delivery vs. customer
    wait time.

    what we want to understand how the delivery cost
    vary vs wait


 3  Ordering:
10  Packing:  printing, inventory, sorting, weighing, packing 10 hours if no issues
 2  Splitting/routing (printing): 2 hours if no issues
 4  inventory
 4  pickups
--
23  hours overhead

10  delivery driving
==
33 hours

        18-11
430 orders
    avg supplier - consumer distance
 41 suppliers
 55 consumers


 */

public class sim {

    private static final String TAG = sim.class.getSimpleName();
    private static Random random = new Random(12737L);

    private static int maxScenarios = 1;

    private static List<Consumer> consumers;
    private static List<Supplier> suppliers;
    private static List<Depot> depots;

    public static void main(String [ ] args) {

        for(int maxOrders = 100; maxOrders < 400; maxOrders += 50) {
            for (int i = 0; i < maxScenarios; ++i) {
                Order.allOrders.clear();
                suppliers = initSuppliers(i);
                consumers = initConsumers(i);
                depots = initDepots(i);
            }

            Log.d(TAG, String.format("<<<<<<<<< maxStops %4d ", maxOrders));

            deliver(maxOrders);
        }
    }

    /**
     * run the clock forward to trigger the orders the
     * customers have setup.  as the orders accumulate they
     * are formed into batches and delivered
     *
     * at one end of the spectrum as each order is triggered
     * it is delivered
     *
     * batch sizes have a lower limit of one order,
     * the number of stops in a batch is an upper limit
     * the total length of the route to deliver is an upper limit
     *
     */
    private static void deliver(int maxOrders) {

        // serialize all the orders, simpler than hunting
        List<Order> orders = new ArrayList<>(Order.allOrders);
        orders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getTime(), o2.getTime());
            }
        });

        // form batches and deliver
        while(orders.size() > 0) {
            Batch batch = new Batch(orders, maxOrders);
            batch.deliver();
        }
    }

    private static List<Consumer> initConsumers(int scenario) {

        List<Consumer> consumers = new ArrayList<>();
        switch (scenario) {
            case 0:
                consumers.add(
                        new Consumer(scenario,
                                new Location(-2, 0),
                                suppliers));
                consumers.add(
                        new Consumer(scenario,
                                new Location(0, 2),
                                suppliers));
                consumers.add(
                        new Consumer(scenario,
                                new Location(2, 0),
                                suppliers));
                consumers.add(
                        new Consumer(scenario,
                                new Location(0, -2),
                                suppliers));
                break;
        }
        return consumers;
    }

    private static List<Supplier> initSuppliers(int scenario) {
        List<Supplier> suppliers = new ArrayList<>();
        switch (scenario) {
            case 0:
                suppliers.add(
                        new Supplier(
                                new Location(-7, 0)));
                break;
        }
        return suppliers;
    }

    private static List<Depot> initDepots(int scenario) {
        List<Depot> depots = new ArrayList<>();
            switch (scenario) {
                case 0:
                    break;
            }
//        depots.add(new Depot());
        return depots;
    }

    public static Random getRandom() {
        return random;
    }
}
