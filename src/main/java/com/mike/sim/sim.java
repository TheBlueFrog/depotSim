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
    public static Random getRandom() {
        return random;
    }

    private static int maxScenarios = 3;

    private static List<Consumer> consumers;
    private static List<Supplier> suppliers;
    private static List<Depot> depots;

    public static void main(String [ ] args) {

        for (int scenario = 2; scenario < maxScenarios; ++scenario) {
            int maxOrders = 400;// maxOrders += 50) {
                Log.d(TAG, String.format("<<<<<<<<< Secenario %d, orders %4d ", scenario, maxOrders));

                suppliers = Supplier.initSuppliers(scenario);
                consumers = Consumer.initConsumers(scenario, suppliers);
                depots = initDepots(scenario);


                deliver(Order.allOrders, maxOrders);
//            }
        }
    }

    private static void deliver(List<Order> orders, int maxOrders) {

        // order the orders by time
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

    private static List<Depot> initDepots(int scenario) {
        List<Depot> depots = new ArrayList<>();
            switch (scenario) {
                case 0:
                    break;
            }
//        depots.add(new Depot());
        return depots;
    }

}
