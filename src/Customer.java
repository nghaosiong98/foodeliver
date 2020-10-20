import java.io.IOException;
import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
    private String name;
    private FileManager orderFileManager = new FileManager("./order.csv");
    private FileManager restaurantFileManager = new FileManager("./restaurant.csv");

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
        FileManager menuFileManager = new FileManager(String.format("./%s-menu.csv", restaurantName));
        String foodDataString = menuFileManager.readById(foodId);
        Food food = new Food(foodDataString);
        double totalPrice = food.getPrice() * quantity;
        Order newOrder;
        switch (method) {
            case "delivery":
                newOrder = new Order(food.getName(), restaurantName, quantity, name, Constant.OrderStatus.OPEN.getStatus(), totalPrice);
                break;
            case "self-collect":
                newOrder = new Order(food.getName(), restaurantName, quantity, name, Constant.OrderStatus.SELF_COLLECT.getStatus(), totalPrice);
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
            Order order = new Order(e);
            System.out.printf("ID: %s [%s] %s - %s Price: %.2f (%s)\n", order.getId(), order.getRestaurantName(), order.getFoodName(), order.getQty(), order.getTotalPrice(), order.getStatus());
        });
        System.out.println("Order end.");
    }

    public void collectOrder(int id) throws IOException {
        String orderDataString = orderFileManager.readById(id);
        Order order = new Order(orderDataString);
        order.setStatus(Constant.OrderStatus.COLLECTED.getStatus());
        orderFileManager.updateById(id, order.toString());
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
        FileManager menuFileManager = new FileManager(String.format("./%s-menu.csv", restaurantName));
        ArrayList<String> foods = menuFileManager.readAll();
        System.out.printf("Below are food sell by restaurant %s: \n", restaurantName);
        for (String foodDataString : foods) {
            Food food = new Food(foodDataString);
            System.out.printf("ID: %s - %s, Price: %.2f\n", food.getId(), food.getName(), food.getPrice());
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
