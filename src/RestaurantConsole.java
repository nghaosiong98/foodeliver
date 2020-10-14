import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestaurantConsole {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter restaurant name: ");
        String restaurantName = consoleReader.readLine();
        Restaurant restaurant = new Restaurant(restaurantName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            switch (inputArr[0]) {
                case "add":
                    restaurant.addMenu(inputArr[1]);
                    break;
                case "delete":
                    restaurant.deleteMenu(Integer.parseInt(inputArr[1]));
                    break;
                case "update":
                    restaurant.updateMenu(Integer.parseInt(inputArr[1]), inputArr[2]);
                    break;
                case "viewMenu":
                    restaurant.viewMenu();
                    break;
                case "viewOrder":
                    restaurant.viewOrder();
                    break;
                case "prepareOrder":
                    restaurant.preparingOrder(Integer.parseInt(inputArr[1]));
                    break;
                case "readyOrder":
                    restaurant.readyOrder(Integer.parseInt(inputArr[1]));
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
