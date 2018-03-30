package com.mike.sim;

public class Location {

    private final double x;
    private final double y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("(%4.0f, %4.0f)", x, y);
    }

    public double distance(Location location) {
        double dx = Math.abs(getX() - location.getX());
        double dy = Math.abs(getY() - location.getY());
        return Math.sqrt((dx * dx) + (dy * dy));
    }
}
