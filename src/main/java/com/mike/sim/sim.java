package com.mike.sim;


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

        for (int scenario = 0; scenario < maxScenarios; ++scenario) {
            int maxOrders = 400;// maxOrders += 50) {
                Log.d(TAG, String.format("<<<<<<<<< Secenario %d, orders %4d ", scenario, maxOrders));

                suppliers = Supplier.setup(scenario);
                consumers = Consumer.setup(scenario, suppliers);
                depots = Depot.setup(scenario);

                for(Consumer consumer : consumers)
                    consumer.setupSchedule(scenario, suppliers);

                deliver(maxOrders);
//            }
        }
    }

    private static void deliver(int maxOrders) {

        // form batches and process, no pro-active S->D

        while(Order.haveOpenOrders()) {
            Batch batch = new Batch(maxOrders);

            ConsumerRun consumerRun = new ConsumerRun(batch);

//            SupplierRun supplierRun = new SupplierRun(consumerRun);
//            supplierRun.drop();

            consumerRun.deliver();
        }
    }

    /** select the depot for this order, depends on
     * global state
     * @param order
     * @return
     */
    public static Depot getDepot(Order order) {
        return depots.get(0);
    }

    /**
     * map the orders of a consumer run into supplier orders
     * @param consumerRun
     * @return
     */
    public static List<Order> getSupplierOrders(ConsumerRun consumerRun) {
        return null;
    }
}
