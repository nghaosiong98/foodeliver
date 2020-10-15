import java.io.IOException;
import java.util.ArrayList;

public class Admin {
    private FileManager riderFileManger = new FileManager("./rider.txt");
    private FileManager orderFileManager = new FileManager("./order.txt");
    private MyQueue riderQueue;
    private final int MAX_RIDER = 10;

    public Admin() {
        this.riderQueue = new MyQueue(Rider.class, MAX_RIDER);
    }

    public void addRider(String riderName) {
        riderQueue.enqueue(new Rider(riderName));
        System.out.printf("Added rider. Name: %s.\n", riderName);
    }

    // assign with order id
    public void assignRider(int orderId) throws IOException {
        if (riderQueue.isEmpty()) {
            System.out.println("No rider available. Please add a rider to proceed.");
            return;
        }
        Rider rider = (Rider) riderQueue.dequeue();
        String dataString = orderFileManager.readById(orderId);
        Order orderTobeUpdated = new Order(dataString);
        orderTobeUpdated.setRiderName(rider.getName());
        orderTobeUpdated.setStatus(Constant.OrderStatus.PENDING.getStatus());
        orderFileManager.updateById(orderId, orderTobeUpdated.toString());
        System.out.printf("Assigned rider %s to order #%s.\n", rider.getName(), orderId);
    }

    public void viewRiderQueue() {
        if (riderQueue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }
        MyQueue<Rider> tempQueue = new MyQueue<>(Rider.class, MAX_RIDER);
        int counter = 0;
        Rider rider;
        while (riderQueue.size() > 0) {
            rider = (Rider) riderQueue.dequeue();
            tempQueue.enqueue(rider);
            System.out.printf("#%s - %s\n", ++counter, rider.getName());
        }
        riderQueue = tempQueue;
    }

    public void viewAllOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readAll();
        orders.forEach(e -> {
            Order order = new Order(e);
            System.out.printf(
                    "ID: %s [%s] %s - %s (%s)\n",
                    order.getId(),
                    order.getRestaurantName(),
                    order.getFoodName(),
                    order.getQty(),
                    order.getStatus()
            );
        });
    }

    private void viewOrderByStatus(String status) throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(6, status);
        orders.forEach(e -> {
            Order order = new Order(e);
            if (order.getStatus().equals(status)) {
                if (order.getRiderName().length() > 0) {
                    System.out.printf(
                            "ID: %s [%s] %s - %s (%s) | Rider: %s\n",
                            order.getId(),
                            order.getRestaurantName(),
                            order.getFoodName(),
                            order.getQty(),
                            order.getStatus(),
                            order.getRiderName()
                    );
                } else {
                    System.out.printf(
                            "ID: %s [%s] %s - %s (%s)\n",
                            order.getId(),
                            order.getRestaurantName(),
                            order.getFoodName(),
                            order.getQty(),
                            order.getStatus()
                    );
                }
            }
        });
    }

    public void viewOpenOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.OPEN.getStatus());
    }

    public void viewPreparingOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.PREPARING.getStatus());
    }

    public void viewCollectedOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.COLLECTED.getStatus());
    }

    public void viewDeliveredOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.DELIVERED.getStatus());
    }

    public void viewReadyOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.READY.getStatus());
    }

    public void viewPendingOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.PENDING.getStatus());
    }

    //TODO: order statistic
}
