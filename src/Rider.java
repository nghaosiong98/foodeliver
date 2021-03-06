import java.io.IOException;
import java.util.ArrayList;

public class Rider implements Comparable<Rider> {
    private String name;
    private FileManager orderFileManager;

    private int orderCount = 0;

    public Rider (String name) {
        this.name = name;
        this.orderFileManager = new FileManager("./order.csv");
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
        System.out.println("End list.");
    }

    // changed order status to delivered
    public void deliverOrder(int id) throws IOException {
        String dataString = orderFileManager.readById(id);
        Order order = new Order(dataString);
        order.setStatus("delivered");
        orderFileManager.updateById(order.getId(), order.toString());
        System.out.printf("Order#%s has been delivered.", order.getId());
    }

    public void checkTurn() throws IOException {
        FileManager riderFileManager = new FileManager("./rider.csv");
        ArrayList<String> riders = riderFileManager.readAll();
        for (int i = 0; i < riders.size(); i++) {
            String[] split = riders.get(i).split(",");
            Rider rider = new Rider(split[1]);
            if (rider.getName().equals(name)) {
                if (i == 0) {
                    System.out.println("You are first in the queue.");
                } else {
                    System.out.printf("%s rider(s) before you.\n", i);
                }
                return;
            }
        }
        System.out.println("You are not in the queue.");
    }

    public void increaseOrderCount() {
        this.orderCount += 1;
    }

    public int getOrderCount() {
        return orderCount;
    }

    @Override
    public int compareTo(Rider o) {
        if (orderCount == o.orderCount)
            return 0;
        else if (orderCount < o.orderCount)
            return 1;
        else
            return -1;
    }
}
