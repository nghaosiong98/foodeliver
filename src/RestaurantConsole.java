import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestaurantConsole {
    public RestaurantConsole(String restaurantName) throws IOException {
        System.out.printf("This is the console of restaurant %s. Please proceed with your command!\n", restaurantName);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Restaurant restaurant = new Restaurant(restaurantName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            try {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Your are missing arguments in the command. Please check again.");
            }

        }
    }
}
