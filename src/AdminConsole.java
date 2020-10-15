import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdminConsole {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Admin admin = new Admin();
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            switch (inputArr[0]) {
                case "add":
                    admin.addRider(inputArr[1]);
                    break;
                case "assign":
                    admin.assignRider(Integer.parseInt(inputArr[1]));
                    break;
                case "viewQueue":
                    admin.viewRiderQueue();
                    break;
                case "viewOrders":
                    admin.viewAllOrder();
                    break;
                case "open":
                    admin.viewOpenOrder();
                    break;
                case "pending":
                    admin.viewPendingOrder();
                    break;
                case "ready":
                    admin.viewReadyOrder();
                    break;
                case "preparing":
                    admin.viewPreparingOrder();
                    break;
                case "delivered":
                    admin.viewDeliveredOrder();
                    break;
                case "collected":
                    admin.viewCollectedOrder();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
