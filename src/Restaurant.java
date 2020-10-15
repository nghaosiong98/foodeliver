import java.io.IOException;
import java.util.ArrayList;

public class Restaurant implements Comparable<Restaurant> {
    private String name;
    private FileManager menuFileManager;
    private FileManager orderFileManager;

    private int orderCount = 0;

    public Restaurant(String name) {
        this.name = name;
        this.menuFileManager = new FileManager(String.format("./%s-menu.txt", this.name));
        this.orderFileManager = new FileManager("./order.txt");
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

    public void addMenu(String foodName) throws IOException {
        menuFileManager.insert(foodName);
        System.out.println("Added successful.");
    }

    public void viewMenu() throws IOException {
        ArrayList<String> rows = menuFileManager.readAll();
        System.out.printf("Below is the menu of %s.\n", name);
        rows.forEach(e -> {
            String[] row = e.split(",");
            System.out.printf("%s. %s\n", row[0], row[1]);
        });
        System.out.println("Menu end.");
    }

    public void deleteMenu(int id) throws IOException {
        menuFileManager.deleteById(id);
        System.out.println("Delete successful.");
    }

    public void updateMenu(int id, String newFoodName) throws IOException {
        String updatedRow = String.format("%s,%s", id, newFoodName);
        menuFileManager.updateById(id, updatedRow);
        System.out.println("Update successful.");
    }

    public void viewOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(1, name);
        System.out.println("Order list:");
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
