import java.util.ArrayList;
import java.util.Scanner;

public class Network {
    public static void main(String[] args) {
        int  connectionsNo, decivesNo;
        ArrayList<Device> currentDevices = new ArrayList<>();

        Scanner input = new Scanner(System.in);
        System.out.println("What is number of WI-FI Connections?");
        connectionsNo= input.nextInt();
        Router router= new Router(connectionsNo);

        System.out.println("What is number of devices Clients want to connect?");
        decivesNo = input.nextInt();
        for (int i= 0; i< decivesNo; i++) {
            Device additionalDevice= new Device(input.next(),input.next(), router);
            currentDevices.add(additionalDevice);
        }

        for (int i= 0; i< decivesNo; i++) {
            currentDevices.get(i).start();
        }
    }
}
