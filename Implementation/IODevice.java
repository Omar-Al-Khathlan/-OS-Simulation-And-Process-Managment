import java.util.Queue;

public class IODevice extends Thread {
	
    // Attributes.
	private Queue<PCB> 	deviceQueue; 	// Has process waiting for IO Bursts.
    private PCB 		proccess;		// 
    
    
    // Methods.
    public boolean addIORequest(PCB p) {
        return deviceQueue.add(p);
    }
    
    // Why is this method needed when we have the above method?
    public void addIORequeest(PCB p) {
        deviceQueue.add(p);
    }

    @Override
    public void run() {
        for (;;) {
            proccess = deviceQueue.poll();
            if (proccess != null)
                doIO();
            else
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
    
    public void doIO() {
        proccess.incCountIO();

        while( !proccess.getBurst().decBurst() ) {
            proccess.incIOTime();            
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        proccess.incIOTime();

        Burst next = proccess.nextBurst();
        if(next == null) {
            proccess.terminate();
        } else {
            proccess.setToReady();
        }
        
    }

    public Queue<PCB> getDeviceQueue() {
        return this.deviceQueue;
    }

    public void setDeviceQueue(Queue<PCB> deviceQueue) {
        this.deviceQueue = deviceQueue;
    }

    public PCB getProccess() {
        return this.proccess;
    }

    public void setProccess(PCB proccess) {
        this.proccess = proccess;
    }

}
