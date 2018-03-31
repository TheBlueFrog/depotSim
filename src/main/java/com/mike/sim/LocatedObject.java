package com.mike.sim;

import java.util.function.Consumer;

public class LocatedObject {

    protected static int nextId = 0;
    protected final Location location;
    protected int id;

    public LocatedObject(Location location) {
        this.location = location;
        this.id = nextId++;
    }

    public double distance(LocatedObject locatedObject) {
        return getLocation().distance(locatedObject.getLocation());
    }

    public Location getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }
}
