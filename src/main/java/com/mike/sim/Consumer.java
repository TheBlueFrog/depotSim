package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends LocatedObject {

    private OrderingSchedule orderingSchedule;

    public Consumer(int scenario, Location location, List<Supplier> suppliers) {
        super(location);
    }

    static List<Consumer> setup(int scenario, List<Supplier> suppliers) {

        List<Consumer> consumers = new ArrayList<>();
        switch (scenario) {
            case 0: {
                Location[] locs = new Location[]{
                        new Location( 8, 0),
                        new Location(10, 2),
                        new Location(12, 0),
                        new Location(10, -2),
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
            case 2: {
                Location[] locs = new Location[]{
                        new Location(-1, 0),
                        new Location(0, 1),
                        new Location(1, 0),
                        new Location(0, -1),
                };
                for(int i = 1; i < 10; ++i) {
                    for (Location loc : locs) {
                        consumers.add(
                                new Consumer(scenario,
                                        new Location((i * loc.getX()) + 20, i * loc.getY()), suppliers));
                    }
                }
            }
            break;
        }
        return consumers;
    }

    public void setupSchedule(int scenario, List<Supplier> suppliers) {
        orderingSchedule = new OrderingSchedule(scenario, this, suppliers);
    }


}
