package com.mike.sim;


import java.util.ArrayList;
import java.util.List;

/* a customer order
 */
public class Order {

    public static List<Order> allOrders = new ArrayList<>();
    private static int nextId = 0;

    private final int id;
    private final double time;
    private final Supplier supplier;
    private final Consumer consumer;

    public Order(double time, Supplier supplier, Consumer consumer) {
        this.id = nextId++;
        this.time = time + sim.getRandom().nextDouble();
        this.supplier = supplier;
        this.consumer = consumer;

        allOrders.add(this);
    }

    public double getTime() {
        return time;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Consumer getConsumer() {
        return consumer;
    }

    public int getId() {
        return id;
    }
}
