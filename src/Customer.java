import java.io.IOException;
import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String name;
    private FileManager orderFileManager = new FileManager("./order.txt");
    private FileManager restaurantFileManager = new FileManager("./restaurant.txt");

    private int orderCount = 0;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void increaseOrderCount () {
        this.orderCount += 1;
    }

    public void placeOrder(String restaurantName, int foodId, int quantity, String method) throws IOException {
        //TODO: change foodName to foodId
        FileManager menuFileManager = new FileManager(String.format("./%s-menu.txt", restaurantName));
        String foodDataString = menuFileManager.readById(foodId);
        Food food = new Food(foodDataString);
        Order newOrder;
        switch (method) {
            case "delivery":
                newOrder = new Order(food.getName(), restaurantName, quantity, name, Constant.OrderStatus.OPEN.getStatus());
                break;
            case "self-collect":
                newOrder = new Order(food.getName(), restaurantName, quantity, name, Constant.OrderStatus.SELF_COLLECT.getStatus());
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

    public void viewAllRestaurants() throws IOException {
        ArrayList<String> restaurantNames = restaurantFileManager.readAll();
        System.out.println("Below are available restaurant(s): ");
        for (String restaurantName : restaurantNames) {
            System.out.println(restaurantName);
        }
        System.out.println("End of list.");
    }

    public void viewMenu(String restaurantName) throws IOException {
        FileManager menuFileManager = new FileManager(String.format("./%s-menu.txt", restaurantName));
        ArrayList<String> items = menuFileManager.readAll();
        System.out.printf("Below are food sell by restaurant %s: \n", restaurantName);
        for (String item : items) {
            String[] split = item.split(",");
            System.out.printf("ID: %s - %s\n", split[0], split[1]);
        }
        System.out.println("End of list.");
    }

    @Override
    public int compareTo(Customer o) {
        if (orderCount == o.orderCount)
            return 0;
        else if (orderCount < o.orderCount)
            return 1;
        else
            return -1;
    }
}
