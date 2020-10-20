import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Admin {
    private FileManager riderFileManger = new FileManager("./rider.csv");
    private FileManager orderFileManager = new FileManager("./order.csv");
    private MyQueue riderQueue;
    private final int MAX_RIDER = 10;

    public Admin() {
        this.riderQueue = new MyQueue(Rider.class, MAX_RIDER);
        riderFileManger.deleteFiles();
    }

    public void addRider(String riderName) throws IOException {
        riderQueue.enqueue(new Rider(riderName));
        updateRiderFile();
        System.out.printf("Added rider. Name: %s.\n", riderName);
    }

    // assign with order id
    public void assignRider(int orderId) throws IOException {
        if (riderQueue.isEmpty()) {
            System.out.println("No rider available. Please add a rider to proceed.");
            return;
        }
        Rider rider = (Rider) riderQueue.dequeue();
        updateRiderFile();
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

    public void viewSelfCollectOrder() throws IOException {
        viewOrderByStatus(Constant.OrderStatus.SELF_COLLECT.getStatus());
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

    private void updateRiderFile() throws IOException {
        riderFileManger.deleteFiles();

        MyQueue<Rider> tempQueue = new MyQueue<>(Rider.class, MAX_RIDER);
        Rider rider;
        while (riderQueue.size() > 0) {
            rider = (Rider) riderQueue.dequeue();
            tempQueue.enqueue(rider);
            riderFileManger.insert(rider.getName());
        }
        riderQueue = tempQueue;
    }
    //TODO: order statistic

    public void viewRiderByHighestDeliveryCount() throws IOException {
        ArrayList<Rider> riders = new ArrayList<>();
        ArrayList<String> orders = orderFileManager.readByField(6, Constant.OrderStatus.DELIVERED.getStatus());
        Boolean notExist;
        for (String orderStringData : orders) {
            notExist = true;
            Order order = new Order(orderStringData);
            for(Rider rider: riders) {
                if (rider.getName().equals(order.getRiderName())) {
                    rider.increaseOrderCount();
                    notExist = false;
                    break;
                }
            }
            if (notExist) {
                Rider newRider = new Rider(order.getRiderName());
                newRider.increaseOrderCount();
                riders.add(newRider);
            }
        }
        Collections.sort(riders);
        for (Rider rider : riders) {
            System.out.printf("%s - %s\n", rider.getName(), rider.getOrderCount());
        }
    }

    public void viewCustomerByHighestOrder() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<String> orders = orderFileManager.readAll();
        Boolean notExist;
        for (String orderStringData : orders) {
            notExist = true;
            Order order = new Order(orderStringData);
            for(Customer customer: customers) {
                if (customer.getName().equals(order.getCustomerName())) {
                    customer.increaseOrderCount();
                    notExist = false;
                    break;
                }
            }
            if (notExist) {
                Customer newCustomer = new Customer(order.getCustomerName());
                newCustomer.increaseOrderCount();
                customers.add(newCustomer);
            }
        }
        Collections.sort(customers);
        for (Customer customer : customers) {
            System.out.printf("%s - %s\n", customer.getName(), customer.getOrderCount());
        }
    }

    public void viewRestaurantByHighestOrder() throws IOException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        ArrayList<String> orders = orderFileManager.readAll();
        Boolean notExist;
        for (String orderStringData : orders) {
            notExist = true;
            Order order = new Order(orderStringData);
            for(Restaurant restaurant: restaurants) {
                if (restaurant.getName().equals(order.getRestaurantName())) {
                    restaurant.increaseOrderCount();
                    notExist = false;
                    break;
                }
            }
            if (notExist) {
                Restaurant newCustomer = new Restaurant(order.getRestaurantName());
                newCustomer.increaseOrderCount();
                restaurants.add(newCustomer);
            }
        }
        Collections.sort(restaurants);
        for (Restaurant restaurant : restaurants) {
            System.out.printf("%s - %s\n", restaurant.getName(), restaurant.getOrderCount());
        }
    }
}
