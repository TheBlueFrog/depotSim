package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Depot extends LocatedObject {

    public Depot(Location location) {
        super(location);
    }

    static List<Depot> setup(int scenario) {
        List<Depot> depots = new ArrayList<>();
            switch (scenario) {
                case 0:
                    depots.add(new Depot(new Location(-7, 0)));
                    break;
            }
        return depots;
    }
}
