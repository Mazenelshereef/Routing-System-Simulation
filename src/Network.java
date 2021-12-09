import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
//------------------------------------------------------------------------------------------------------------------------------
 class Semaphore {

    protected int semaphore = 0 ;
    public void setValue(int _semaphore) {
        this.semaphore = _semaphore;
    }

    public int getValue() {
        return semaphore;
    }

    protected Semaphore(int _semaphore) {
        semaphore = _semaphore;
    }

    public synchronized void wait(Device _device){
        semaphore--;

        if (semaphore < 0){


            try{
                System.out.println("(" +_device.getDevName() +")" + " (" + _device.getDevType() + ")" + " arrived and waiting");
                wait();
            }
            catch (InterruptedException ignored){}

        }
        else{
            System.out.println("(" +_device.getDevName() +")" + " (" + _device.getDevType() + ")" + " arrived");
        }

    }

    public synchronized void signal() {
        semaphore++;

        if (semaphore <= 0){

            notify();

        }
    }
}

//------------------------------------------------------------------------------------------------------------------------------
class Device extends Thread{

    private String devName;
    private String devType;
    public int connectionNo;
    private Router router;

    public Device(String _devName, String _devType, Router _router){
     this.devName = _devName;
     this.devType = _devType;
     this.router = _router;
    }

    public void setConnectionNo(int connectionNo) {
        this.connectionNo = connectionNo;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public String getDevName() {
        return devName;
    }

    public String getDevType() {
        return devType;
    }

    public int getConnectionNo() {
        return connectionNo;
    }

    public Router getRouter() {
        return router;
    }

    public void connect() throws InterruptedException {
        router.occupyConnection(this);
    }

    public void perfomOnlineActivity(){
        router.performActivity(this);
    }

    public void logOut(){
        router.releaseConnection(this);
    }


    @Override
    public String toString() {
        return "Device{" +
                "devName='" + devName + '\'' +
                ", devType='" + devType + '\'' +
                ", connectionNo=" + connectionNo +
                ", router=" + router +
                '}';
    }

    @Override
    public void run() {
       router.connect(this);
    }

}

//------------------------------------------------------------------------------------------------------------------------------

class Router {
    public ArrayList <Device> Devices;
    public int connectionsMax;
    public Semaphore semaphore;

    Router(int _noOFConnections) {
        Devices = new ArrayList<>();
        this.connectionsMax = _noOFConnections;
        semaphore = new Semaphore(connectionsMax);
    }

    public ArrayList<Device> getDevices() {
        return Devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        Devices = devices;
    }

    public synchronized void occupyConnection(Device device) throws InterruptedException {
        if(getDevices().size() == connectionsMax){
        for (int i = 0; i < connectionsMax; i++) {
            if (Devices.get(i) == null) {
                device.setConnectionNo(i+1);
                Devices.set(i,device);
                break;
            }
        }
        }
        else {
            device.setConnectionNo(Devices.size() +1);
            Devices.add(device);
        }
        printOutput("Connection " + device.connectionNo + ": " + device.getDevName() + " Occupied");
    }

        public synchronized void releaseConnection (Device device) {
            for (int i = 0; i < connectionsMax; i++) {
                if (Devices.get(i) == device)
                    Devices.remove(i);
                break;
            }
            printOutput("Connection " + device.connectionNo + ": " + device.getDevName() + " Logged out");
            semaphore.signal();
        }
        public void login (Device dev){
            printOutput("Connection " + dev.connectionNo + ": " + dev.getDevName() + " Log in");
        }

        public void performActivity(Device dev){
            printOutput("connection "+ dev.connectionNo + ": " + dev.getDevName() + " performs online activity");
        }

        public synchronized void printOutput(String output) {
        System.out.println(output);
        try {
            FileWriter writer = new FileWriter("fileOutput.txt", true);
            writer.write(output + '\n');
            writer.close();
        }
        catch (IOException e) {
        }
    }


    public void connect(Device dev) {
        try {
            semaphore.wait(dev);
            Thread.sleep(1000);
            occupyConnection(dev);
            Thread.sleep(1000);
            login(dev);
            Thread.sleep(1000);
            performActivity(dev);
            Thread.sleep(1000);
            releaseConnection(dev);

        }
        catch (InterruptedException e){
        }

        }
    }

    //------------------------------------------------------------------------------------------------------------------------------
    
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