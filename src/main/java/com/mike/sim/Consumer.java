package com.mike.sim;

import java.util.List;

public class Consumer extends LocatedObject {

    private final OrderingSchedule orderingSchedule;

    public Consumer(int scenario, Location location, List<Supplier> suppliers) {
        super(location);

        this.orderingSchedule = new OrderingSchedule(scenario, this, suppliers);
    }

    public List<Order> getOrders() {
        return orderingSchedule.getOrders();
    }

}
