import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RiderConsole {
    public static void main(String[] args) throws IOException {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please enter rider name: ");
        String riderName = consoleReader.readLine();
        Rider rider = new Rider(riderName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            switch (inputArr[0]) {
                case "viewOrder":
                    rider.viewOrder();
                    break;
                case "deliver":
                    rider.deliverOrder(Integer.parseInt(inputArr[1]));
                    break;
                case "turn":
                    rider.checkTurn();
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }
}
