import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomerConsole {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter customer name: ");
        String customerName = consoleReader.readLine();
        Customer customer = new Customer(customerName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            switch (inputArr[0]) {
                case "place":
                    // restaurantName, foodName, quantity
                    customer.placeOrder(inputArr[1],inputArr[2],Integer.parseInt(inputArr[3]),inputArr[4]);
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
        }
    }
}
