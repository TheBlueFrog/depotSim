package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

/*
    we precompute all the orders of a customer, for simplicity
 */
public class OrderingSchedule {

    public OrderingSchedule(int scenario, Consumer consumer, List<Supplier> suppliers) {
        switch(scenario) {
            case 0:
            case 1:
            case 2: {
                // setup a bunch of orders spaced uniformly along the time line
                // the supplier is chosen randomly
                double time = 0;
                for(int i = 0; i < 1000; i++) {
                    Order.createOpenOrder(
                            time,
                            suppliers.get(sim.getRandom().nextInt(suppliers.size())),
                            consumer);

                    time = time + 1;
                }
            }
            break;
        }
    }
}
