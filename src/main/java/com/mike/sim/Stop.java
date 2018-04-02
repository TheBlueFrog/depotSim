package com.mike.sim;

import java.util.List;

public class Stop extends LocatedObject {

    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    static public enum Action {
        Delivery,
        Pickup,
        Stop
    };
    private Action action;

    @Override
    public String toString() {
        return Integer.toString(getId()); // location.toString();
    }

    public Stop(Supplier supplier, List<Order> orders) {
        super(supplier.getLocation());

        action = Action.Pickup;
        this.orders = orders;
    }

    public Stop(Consumer consumer, List<Order> orders) {
        super(consumer.getLocation());

        action = Action.Delivery;
        this.orders = orders;
    }

    public boolean isPickup() {
        return action.equals(Action.Pickup);
    }
    public boolean isDelivery() {
        return action.equals(Action.Delivery);
    }

}
