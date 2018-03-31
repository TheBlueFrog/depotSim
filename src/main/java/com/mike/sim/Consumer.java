package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends LocatedObject {

    private final OrderingSchedule orderingSchedule;

    public List<Order> getOrders() {
        return orderingSchedule.getOrders();
    }

    public Consumer(int scenario, Location location, List<Supplier> suppliers) {
        super(location);

        this.orderingSchedule = new OrderingSchedule(scenario, this, suppliers);
    }

    static List<Consumer> initConsumers(int scenario, List<Supplier> suppliers) {

        // the Order class hangs on to all the orders, for performance

        Order.allOrders.clear();

        List<Consumer> consumers = new ArrayList<>();
        switch (scenario) {
            case 0: {
                Location[] locs = new Location[]{
                        new Location(-2, 0),
                        new Location(0, 2),
                        new Location(2, 0),
                        new Location(0, -2),
                };
                for (Location loc : locs) {
                    consumers.add(
                            new Consumer(scenario, loc, suppliers));
                }
            }
            break;
            case 1: {
                Location[] locs = new Location[]{
                        new Location(-2, 0),
                        new Location(0, 2),
                        new Location(2, 0),
                        new Location(0, -2),
                        new Location(-3, 0),
                        new Location(0, 3),
                        new Location(3, 0),
                        new Location(0, -3),
                };
                for (Location loc : locs) {
                    consumers.add(
                            new Consumer(scenario, loc, suppliers));
                }
            }
            break;
        }
        return consumers;
    }

}
