import java.io.IOException;
import java.util.ArrayList;

public class Rider {
    private String name;
    private FileManager orderFileManager;

    public Rider (String name) {
        this.name = name;
        this.orderFileManager = new FileManager("./order.txt");
    }

    public String getName() {
        return name;
    }

    // view all order by name
    public void viewOrder() throws IOException {
        ArrayList<String> orders = orderFileManager.readByField(5, name);
        System.out.println("Your orders:");
        orders.forEach(e -> {
            String[] row = e.split(",");
            System.out.printf("ID: %s [%s] %s - %s (%s)\n", row[0], row[1], row[2], row[3], row[6]);
        });
    }

    // changed order status to delivered
    public void deliverOrder(int id) throws IOException {
        String dataString = orderFileManager.readById(id);
        Order order = new Order(dataString);
        order.setStatus("delivered");
        orderFileManager.updateById(order.getId(), order.toString());
        System.out.printf("Order#%s has been delivered.", order.getId());
    }

    //TODO: check how many turns
}
