import java.io.IOException;
import java.util.ArrayList;

public class Customer {
    private String name;
    private FileManager orderFileManager = new FileManager("./order.txt");

    public Customer(String name) {
        this.name = name;
    }

    public void placeOrder(String restaurantName, String foodName, int quantity, String method) throws IOException {
        Order newOrder;
        switch (method) {
            case "delivery":
                newOrder = new Order(foodName, restaurantName, quantity, name, Constant.OrderStatus.OPEN.getStatus());
                break;
            case "self-collect":
                newOrder = new Order(foodName, restaurantName, quantity, name, Constant.OrderStatus.SELF_COLLECT.getStatus());
                break;
            default:
                System.out.println("You entered invalid method.\nPlease choose either \"delivery\"/\"self-collect\"");
                return;
        }
        orderFileManager.insert(newOrder.toInsertString());
        System.out.println("Order placed.");
    }

    public void viewOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(4, name);
        System.out.println("Your orders:");
        orders.forEach(e -> {
            String[] row = e.split(",");
            System.out.printf("ID: %s [%s] %s - %s (%s)\n", row[0], row[1], row[2], row[3], row[6]);
        });
        System.out.println("Order end.");
    }

    public void collectOrder(int id) throws IOException {
        String order = orderFileManager.readById(id);
        String[] split = order.split(",");
        Order collectedOrder = new Order(Integer.parseInt(split[0]), split[1], split[2], Integer.parseInt(split[3]), split[4], split[5], "collected");
        orderFileManager.updateById(id, collectedOrder.toString());
        System.out.println("Order collected.");
    }
}
