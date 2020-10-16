import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomerConsole {
    public CustomerConsole(String customerName) throws IOException {
        System.out.printf("Welcome back %s, Please proceed with your command!\n", customerName);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Customer customer = new Customer(customerName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            try {
                switch (inputArr[0]) {
                    case "listRestaurant":
                        customer.viewAllRestaurants();
                        break;
                    case "listMenu":
                        customer.viewMenu(inputArr[1]);
                        break;
                    case "place":
                        // restaurantName, foodId, quantity, method ("delivery"/"self-collect")
                        customer.placeOrder(inputArr[1], Integer.parseInt(inputArr[2]), Integer.parseInt(inputArr[3]), inputArr[4]);
                        break;
                    case "viewOrder":
                        customer.viewOrder();
                        break;
                    case "collect":
                        customer.collectOrder(Integer.parseInt(inputArr[1]));
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
