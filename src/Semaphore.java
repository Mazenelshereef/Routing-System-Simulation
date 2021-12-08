public class Semaphore {

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