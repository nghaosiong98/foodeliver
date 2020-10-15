import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RiderConsole {
    public RiderConsole(String riderName) throws IOException {
        System.out.printf("Welcome back %s, Please proceed with your command!\n", riderName);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        Rider rider = new Rider(riderName);
        String input;
        while (!(input = consoleReader.readLine()).toLowerCase().equals("exit") ) {
            String[] inputArr = input.split(",");
            try {
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
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Your are missing arguments in the command. Please check again.");
            }
        }
    }
}
