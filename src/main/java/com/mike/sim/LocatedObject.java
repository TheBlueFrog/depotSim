package com.mike.sim;

import java.util.function.Consumer;

public class LocatedObject {

    protected final Location location;

    public LocatedObject(Location location) {
        this.location = location;
    }

    public double distance(LocatedObject locatedObject) {
        return getLocation().distance(locatedObject.getLocation());
    }

    public Location getLocation() {
        return location;
    }
}
