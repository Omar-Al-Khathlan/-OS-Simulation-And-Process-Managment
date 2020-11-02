
public class CPU extends Thread {
    
	// Attributes.
	private IODevice 	ioDevice;
    private PCB 		currentActiveProcess;
    private static int 	busyTime;
    private static int 	idleTime;
    
    // Constructor.
    CPU(IODevice ioDevice) {
    	
        this.ioDevice = ioDevice;
        this.currentActiveProcess = null;
        CPU.busyTime = 0;
        CPU.idleTime = 0;
    }
    
    // Methods.
    @Override
    public void run() {
    	
        while(true) {
            currentActiveProcess = RAM.getReadyQueue();
            if( currentActiveProcess != null ) {
                ExecuteProcess();
            } else {
                try {
                    sleep(1);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                Clock.incTime();
                CPU.idleTime++;
            }
        }
        
    }
    
    // Process execution.
    private void ExecuteProcess() {
		
    	PCB executingProcess = currentActiveProcess;
    	executingProcess.setState(ProcessState.READY);
    	CPUBurst executingBurst = (CPUBurst) executingProcess.nextBurst();
    	executingProcess.incCountCPU();
    	
    	while( executingBurst.decBurst() ) {
    		
    		executingProcess.incCPUTime();
    		CPU.busyTime++;
    		
    		try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    		
    		Clock.incTime();
    		if(currentActiveProcess.getPId() != executingProcess.getPId()) {
    			executingProcess = currentActiveProcess;
    			if( executingBurst.getBurst() > 0 )
    				executingProcess.returnBurst(((Burst)executingBurst));
    			executingBurst = (CPUBurst) executingProcess.nextBurst();
    		}
    	}
    	// Get the next burst and load it in PCB
        Burst nextBurst = executingProcess.nextBurst();
        
        if(nextBurst == null) {
            // This process finished all of its bursts normally
            executingProcess.terminate();
        }
        int memoryValue = executingBurst.getMemory();
        
        int oldTotalRamUsage = RAM.getSize();

        if(memoryValue != 0)
            RAM.handleMemoryValue(executingProcess, memoryValue);

        // If anything changed in the process, return
        if(executingProcess.getState().equals(ProcessState.RUNNING) && nextBurst instanceof IOBurst) {
            // Handle IO Burst
            executingProcess.setState(ProcessState.WAITING);
            ioDevice.addIORequest(this.currentActiveProcess);
        }
	}

	// Increment methods. 
    public static void IncBusyTime() {
		CPU.busyTime += 1;
	}
    
    public static void incIdleTime() {
		CPU.idleTime += 1;
	}
    
    // Setters and Getters
	public IODevice getIoDevice() {
		return ioDevice;
	}
	public void setIoDevice(IODevice ioDevice) {
		this.ioDevice = ioDevice;
	}
	public PCB getCurrentActiveProcess() {
		return currentActiveProcess;
	}
	public void setCurrentActiveProcess(PCB currentActiveProcess) {
		this.currentActiveProcess = currentActiveProcess;
	}
	public static int getBusyTime() {
		return busyTime;
	}
	
	public static int getIdleTime() {
		return idleTime;
	}
    
}