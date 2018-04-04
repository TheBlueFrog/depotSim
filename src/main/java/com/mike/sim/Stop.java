package com.mike.sim;

import java.util.ArrayList;
import java.util.List;

public class Stop extends LocatedObject {

    static public enum Action {
        Drop,
        Pick,
        Stop
    };
    private Action action;

    public Action getAction() {
        return action;
    }

    private List<Order> orders = new ArrayList<>();
    public List<Order> getOrders() {
        return orders;
    }

    public boolean isPickup() {
        return action.equals(Action.Pick);
    }
    public boolean isDelivery() {
        return action.equals(Action.Drop);
    }

    @Override
    public String toString() {
        return Integer.toString(getId()); // location.toString();
    }

    public Stop(Supplier supplier, List<Order> orders) {
        super(supplier.getLocation());

        action = Action.Pick;
        this.orders.addAll(orders);
    }

    public Stop(Consumer consumer, List<Order> orders) {
        super(consumer.getLocation());

        action = Action.Drop;
        this.orders.addAll(orders);
    }

//    public Stop(Consumer consumer, List<ConsumerOrder> consumerOrders) {
//        super(consumer.getLocation());
//
//        action = Action.Drop;
//        this.orders = consumerOrders;
//    }


}
