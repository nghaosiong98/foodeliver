import java.io.IOException;
import java.util.ArrayList;

public class Restaurant implements Comparable<Restaurant> {
    private String name;
    private FileManager menuFileManager;
    private FileManager orderFileManager = new FileManager("./order.txt");
    private FileManager restaurantFileManager = new FileManager("./restaurant.txt");;

    private int orderCount = 0;

    public Restaurant(String name) {
        this.name = name;
        this.menuFileManager = new FileManager(String.format("./%s-menu.txt", this.name));

        try {
            ArrayList<String> restaurantNames = restaurantFileManager.readAll();
            if (!restaurantNames.contains(name)) {
                restaurantFileManager.insertWithoutId(name);
            }
        } catch (IOException e) {
            System.out.println("./restaurant.txt not found.");
        }
    }

    public int getOrderCount() {
        return orderCount;
    }

    public String getName() {
        return name;
    }

    public void increaseOrderCount() {
        this.orderCount += 1;
    }

    public void addMenu(String foodName, double price) throws IOException {
        Food newFood = new Food(foodName, price);
        menuFileManager.insert(newFood.toInsertString());
        System.out.println("Added successful.");
    }

    public void viewMenu() throws IOException {
        ArrayList<String> rows = menuFileManager.readAll();
        System.out.printf("Below is the menu of %s.\n", name);
        rows.forEach(e -> {
            Food food = new Food(e);
            System.out.printf("%s. %s - RM%s\n", food.getId(), food.getName(), food.getPrice());
        });
        System.out.println("Menu end.");
    }

    public void deleteMenu(int id) throws IOException {
        menuFileManager.deleteById(id);
        System.out.println("Delete successful.");
    }

    public void updateMenu(int id, String newFoodName, double price) throws IOException {
        Food food = new Food(id, newFoodName, price);
        menuFileManager.updateById(id, food.toString());
        System.out.println("Update successful.");
    }

    public void viewOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(1, name);
        System.out.println("Order list:");
        orders.forEach(e -> {
            Order order = new Order(e);
            System.out.printf(
                    "ID: %s [%s] %s - %s Price: %s (%s)\n",
                    order.getId(),
                    order.getRestaurantName(),
                    order.getFoodName(),
                    order.getQty(),
                    order.getTotalPrice(),
                    order.getStatus()
            );
        });
        System.out.println("Order end.");
    }

    private void updateOrderStatus(int id, String status) throws IOException {
        String dataString = orderFileManager.readById(id);
        Order selectedOrder = new Order(dataString);
        selectedOrder.setStatus(status);
        orderFileManager.updateById(id, selectedOrder.toString());
    }

    public void preparingOrder(int id) throws IOException {
        updateOrderStatus(id, "preparing");
        System.out.printf("Order#%s is preparing.\n", id);
    }

    public void readyOrder(int id) throws IOException {
        updateOrderStatus(id, "ready");
        System.out.printf("Order#%s is ready.\n", id);
    }

    @Override
    public int compareTo(Restaurant o) {
        if (orderCount == o.orderCount)
            return 0;
        else if (orderCount < o.orderCount)
            return 1;
        else
            return -1;
    }
}
