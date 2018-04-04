package com.mike.sim;

/*
 * batch sizes have a lower limit of one order,
 * the number of stops in a batch is an upper limit
 * the total length of the route to drop is an upper limit
 */
public class ConsumerRun {

    private static final String TAG = ConsumerRun.class.getSimpleName();
    private static int nextId = 0;

    private final int id;

    private AnnealData annealer;

    /** setup run for consumers */
    public ConsumerRun(Batch batch) {
        id = nextId++;

        annealer = new AnnealData();
        annealer.setupConsumerRun(batch.getOrders());
        annealer.anneal();
    }

    // iterate the route, accumulating the cost
    public void deliver() {
        StringBuilder sb = new StringBuilder();
        double distance = 0;
        Location curLoc = annealer.getStops().get(0).getLocation();

        for(Stop stop : annealer.getStops()) {
            distance += stop.getLocation().distance(curLoc);
            curLoc = stop.getLocation();

            switch (stop.getAction()) {
                case Pick: {
                    for (Order order : stop.getOrders()) {
                        order.pick(this);
                        sb.append(String.format("(%03d %04d) ", order.getConsumer().getId(), order.getId()));
                    }

                    Log.d(TAG, String.format("%4d %5.1f Pick %s", id, distance, sb.toString()));
                }
                break;
                case Drop: {
                    for (Order order : stop.getOrders()) {
                        order.drop(this);
                        sb.append(String.format("(%03d %04d) ", order.getConsumer().getId(), order.getId()));
                    }
                    Log.d(TAG, String.format("%4d %5.1f Drop %s", id, distance, sb.toString()));
                }
                break;
                case Stop: {

                }
                break;
            }
        }

    }


//    /** construct a set of orders for suppliers that will
//     * satisfy out set of consumer orders
//     *
//     * @return
//     */
//    public List<Order> getSupplierOrders() {
//        List<Order> supplierOrderss = new ArrayList<>();
//        for (Order consumerOrder : orders) {
//            double timeAtDepot = orders.get(0).getTime();    // before the start of the run
//            Depot depot = sim.getDepot(consumerOrder);
//            new Order(consumerOrder, timeAtDepot, depot);
//        }
//        return supplierOrderss;
//    }

}
