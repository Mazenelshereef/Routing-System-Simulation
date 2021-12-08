import java.util.ArrayList;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) throws InterruptedException {
        int  numberOfConnections, numberOfDecives;
        ArrayList<Device> devices = new ArrayList<>();

        Scanner input = new Scanner(System.in);

        System.out.println("What is number of WI-FI Connections?");
        numberOfConnections = input.nextInt();
        Router router = new Router(numberOfConnections);

        System.out.println("What is number of devices Clients want to connect?");
        numberOfDecives = input.nextInt();


        for (int i = 0; i < numberOfDecives; i++) {
            Device newDevice = new Device(input.next(),input.next(), router);
            devices.add(newDevice);
        }

        for (int i = 0; i < numberOfDecives; i++) {

            devices.get(i).start();
        }
    }
}
