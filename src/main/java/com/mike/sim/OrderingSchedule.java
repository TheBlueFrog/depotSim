package com.mike.sim;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
    we precompute all the orders of a customer, for simplicity
 */
public class OrderingSchedule {

    private final List<Order> orders = new ArrayList<>();

    public OrderingSchedule(int scenario, Consumer consumer, List<Supplier> suppliers) {
        switch(scenario) {
            case 0:
            case 1: {
                // setup a bunch of orders spaced randomly (uniform distribution) along the time line
                double time = 0;
                for(int i = 0; i < 1000; i++) {
                    Order order = new Order(
                            time,
                            suppliers.get(sim.getRandom().nextInt(suppliers.size())),
                            consumer);
                    orders.add(order);

                    time = order.getTime();
                }
            }
            break;
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
}
