import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Router {
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