import java.io.IOException;
import java.util.ArrayList;

public class Customer {
    private String name;
    private FileManager orderFileManager = new FileManager("./order.txt");

    public Customer(String name) {
        this.name = name;
    }

    public void placeOrder(String restaurantName, String foodName, int quantity) throws IOException {
        Order newOrder = new Order(foodName, restaurantName, quantity, name, "pending");
//        String row = String.format("%s,%s,%s,%s,%s", restaurantName, foodName, quantity, name, "pending");
        orderFileManager.insert(newOrder.toRequestString());
        System.out.println("Order placed.");
    }

    public void viewOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(4, name);
        System.out.println("Your orders:");
        orders.forEach(e -> {
            String[] row = e.split(",");
            System.out.printf("ID: %s [%s] %s - %s (%s)\n", row[0], row[1], row[2], row[3], row[5]);
        });
        System.out.println("Order end.");
    }

    public void collectOrder(int id) throws IOException {
        String order = orderFileManager.readById(id);
        String[] split = order.split(",");
        Order collectedOrder = new Order(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]), split[4], "collected");
        orderFileManager.updateById(id, collectedOrder.toString());
        System.out.println("Order collected.");
    }
}
