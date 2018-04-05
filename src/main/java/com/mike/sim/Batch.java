package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

/*
 * batch sizes have a lower limit of one order,
 * the number of stops in a batch is an upper limit
 * the total length of the route to drop is an upper limit
 */
public class Batch {

    private static final String TAG = Batch.class.getSimpleName();
    private static int nextId = 0;

    private final int id;
    private final int scenario;
    private List<Order> orders = new ArrayList<>();

    /** given a batch of consumer orders decide how this will
     * be satisfied.
     *
     * We can get everything direct from the supplier
     * We can setup a supply run for depots and get stuff
     *  from the depots
     * etc.
     *
     * magic here
     * @param type
     */

    public List<Order> getOrders(Order.State type) {
        switch (scenario) {
            case 0: {
                switch(type) {
                    case InConsumerRun:
                        // everything is direct from the supplier which
                        // is the default order structure
                        return orders;
                    case InDepotRun:
                        return new ArrayList<>();
                }
            }
//            break;
            case 1: {
                // everything is delivered from the depot
                switch(type) {
                    case InConsumerRun:
                        // everything is from the depot
                        // is the default order structure
                        return orders;
                    case InDepotRun:
                        return new ArrayList<>();
                }
            }
            case 2:
                assert false;
                break;
        }
        return null;
    }

    /** given open orders carve off a batch */
    public Batch(int scenario, int maxOrders) {
        id = nextId++;
        this.scenario = scenario;
        orders = Order.getBatch(maxOrders);
    }

}
