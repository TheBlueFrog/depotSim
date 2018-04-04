package com.mike.sim;


import java.util.*;

/* a order
    this starts with a customer,  may go to a supplier or depot
    and back to the customer.  essentially is handed from one
    agent to another until completed
 */
public class Order  {

    private static int nextId = 0;

    static public enum State {
        Open,
        InBatch,
        InConsumerRun,
        Picked,
        Delivered,
    };


    // bit complex
    private static Map<Integer, Order> ordersById = new HashMap<>();
    private static List<Order> closedOrders = new ArrayList<>();
    private static List<Order> openOrders = new ArrayList<>();

    public static Order getById(int id) {
        return ordersById.get(id);
    }

    public static void sortOpenOrders() {

        // order the orders by time
        openOrders.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return Double.compare(o1.getTime(), o2.getTime());
            }
        });
    }

    static public void createOpenOrder(double time, Supplier supplier, Consumer consumer) {
        new Order(time, supplier, consumer);
    }

    public static boolean haveOpenOrders() {
        return openOrders.size() > 0;
    }

    public static List<Order> getBatch(int maxOrders) {
        List<Order> od = new ArrayList<>();
        while((openOrders.size() > 0) && (od.size() < maxOrders)) {
            Order order = openOrders.remove(0);
            order.state = State.InBatch;
            od.add(order);
        }
        return od;
    }


    private int id;
    private State state;
    private double time;

    private Supplier supplier;
    private Consumer consumer;

    public int getId() {
        return id;
    }
    public double getTime() {
        return time;
    }
    public Supplier getSupplier() {
        return supplier;
    }
    public Consumer getConsumer() {
        return consumer;
    }

    public void setState(State state) {
        this.state = state;
    }


    private Order(double time, Supplier supplier, Consumer consumer) {
        this.id = nextId++;
        this.state = State.Open;
        this.time = time + sim.getRandom().nextDouble();

        this.supplier = supplier;
        this.consumer = consumer;

        openOrders.add(this);
        ordersById.put(this.id, this);
    }

    public void drop(ConsumerRun consumerRun) {
        setState(Order.State.Delivered);
        closedOrders.add(this);
    }

    public void pick(ConsumerRun consumerRun) {
        setState(Order.State.Picked);
    }


}
