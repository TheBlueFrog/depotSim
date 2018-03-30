package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends LocatedObject {

    private static int nextId = 0;

    private final OrderingSchedule orderingSchedule;
    private int id;

    public Consumer(int scenario, Location location, List<Supplier> suppliers) {
        super(location);
        this.id = nextId++;

        this.orderingSchedule = new OrderingSchedule(scenario, this, suppliers);
    }

    public List<Order> getOrders() {
        return orderingSchedule.getOrders();
    }

    public int getId() {
        return id;
    }
}
