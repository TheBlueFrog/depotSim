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
    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    /** given open orders carve off a batch */
    public Batch(int maxOrders) {
        id = nextId++;

        orders = Order.getBatch(maxOrders);
    }

}
