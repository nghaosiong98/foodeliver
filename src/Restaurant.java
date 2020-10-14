import java.io.IOException;
import java.util.ArrayList;

public class Restaurant {
    private String name;
    private FileManager menuFileManager;
    private FileManager orderFileManager;

    public Restaurant(String name) {
        System.out.println("Initialising restaurant " + name);
        this.name = name;
        this.menuFileManager = new FileManager(String.format("./%s-menu.txt", this.name));
        this.orderFileManager = new FileManager("./order.txt");
        System.out.println("Initialisation done.");
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
}
