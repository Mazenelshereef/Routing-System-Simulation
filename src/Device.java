

public class Device extends Thread{

    private String devName;
    private String devType;
    public int connectionNo;
    private Router router;

    public Device(String _devName, String _devType, Router _router){
     this.devName = _devName;
     this.devType = _devType;
     this.connectionNo = 1;
     this.router = _router;
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
