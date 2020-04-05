import java.util.LinkedList;
import java.util.Queue;

public class PCB {

    // Attributes
    private int 			pId;				// Process ID.
    private String 			pName;				// Process Name.
    private int 			loadTime;			// The time when the process entered the ready queue.
    private int 			countCPU;			// Number of times that this process entered the RUNNING state.
    private int 			CPUTime;			// Number of time ticks this process is in the CPU.
    private int 			countIO;			// Number of times that this process entered the WAITING state.
    private int 			IOTime;				// Number of time ticks this process is in an IO device.
    private int 			countWQ;			// 
    private int 			countPrempt;		// 
    private int 			exitTime;			// The time when this process was TERMINATED/KILLED.
    private ProcessState 	state;				// Stores the current state of the process.
    private int 			size;				// The total size of the program (in Bytes).
    private Burst 			burst; 				// Stores the burst which is currently executing. 'Used to know the burst which the process is in.'
    private Queue<Burst> 	queueBursts;		// A queue which stores the sequence of bursts that a process needs to complete.

    
    // Constructor.
    public PCB(String pName, int pId, Queue<Burst> queue, int size) {
    	
        this.pId = pId;
        this.pName = pName;
        
        this.loadTime = -1;
        this.exitTime = -1;
        
        this.countCPU = 0;
        this.CPUTime = 0;
        this.countIO = 0;
        this.IOTime = 0;
        this.countWQ = 0;
        this.countPrempt = 0;
        
        this.size = size;
        this.state = ProcessState.WAITING;
        this.burst = queue.poll();
        this.queueBursts = queue;
    }

    
    
    // Methods.
    public void printProccess() {
        System.out.println("----------------------------------------" + "       Proccess ID =" + getPId() + "\n"
                + " Proccess Name =" + getPName() + "\n" + " Load Time =" + getLoadTime() + "\n" + " Times was in CPU="
                + getCountCPU() + "\n" + " Time spent in CPU =" + getCPUTime() + "\n" + " Number of IO preformed ="
                + getCountIO() + "\n" + " Time spent doing IO =" + getIOTime() + "\n" + " Times it was Waiting ="
                + getCountWQ() + "\n" + " Number of times it was prempted=" + getCountPrempt() + "\n"
                + " Ternimate time ='" + getExitTime() + "\n" + " Final State='" + getState() + "\n"
                + "----------------------------------------");
    }

    @Override
    public String toString() {
        return "----------------------------------------" + " Proccess ID =" + getPId() + "\n" + " Proccess Name ="
                + getPName() + "\n" + " Load Time =" + getLoadTime() + "\n" + " Times was in CPU=" + getCountCPU()
                + "\n" + " Time spent in CPU =" + getCPUTime() + "\n" + " Number of IO preformed =" + getCountIO()
                + "\n" + " Time spent doing IO =" + getIOTime() + "\n" + " Times it was Waiting =" + getCountWQ() + "\n"
                + " Number of times it was prempted=" + getCountPrempt() + "\n" + " Ternimate time ='"
                + getExitTime() + "\n" + " Final State='" + getState() + "\n"
                + "----------------------------------------";
    }
    
    public void returnBurst(Burst b) {
    	
    	Queue<Burst> newQueueBursts = new LinkedList<Burst>();
    	newQueueBursts.add(b);
    	while(!queueBursts.isEmpty())
    		newQueueBursts.add(queueBursts.poll());
    }
    
    public Burst nextBurst() {

        burst = queueBursts.poll();
        return burst;
    }

    public void terminate() {
    	
    	this.setState(ProcessState.TERMINATED);
        this.exitTime = Clock.getTime();
        RAM.removeSize(this.size);
    }

    public void kill() {
    	
        this.setState(ProcessState.KILLED);
        this.exitTime = Clock.getTime();
        RAM.removeSize(this.size);
    }

    public void setToWaiting() {
    	
    	this.setState(ProcessState.WAITING);
        
        // ------------------------------------------------------------------------------ Possible change.
        // Shouldn't this be 'this.incCountIO();'?
        this.incCountWQ();
        
        RAM.removeSize(this.size);
        RAM.addWaitingQueue(this);
    }

    public void setToReady() {
    	
    	this.setState(ProcessState.READY);
        RAM.addReadyQueue(this);
    }


    // Setters and getters.
    public int getPId() {
        return this.pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }

    public String getPName() {
        return this.pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public int getLoadTime() {
        return this.loadTime;
    }

    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }

    public int getCountCPU() {
        return this.countCPU;
    }

    public void incCountCPU() {
        this.countCPU = countCPU + 1;
    }

    public int getCPUTime() {
        return this.CPUTime;
    }

    public void setCPUTime(int CPUTime) {
        this.CPUTime = CPUTime;
    }
    public void incCPUTime() {
        this.CPUTime += 1;
    }

    public int getCountIO() {
        return this.countIO;
    }

    public void incCountIO() {
        this.countIO += 1;
    }

    public int getIOTime() {
        return this.IOTime;
    }

    public void setIOTime(int IOTime) {
        this.IOTime = IOTime;
    }
    public void incIOTime() {
        this.IOTime += 1;
    }
    public int getCountWQ() {
        return this.countWQ;
    }

    public void incCountWQ() {
        this.countWQ = 1 + countWQ;
    }

    public int getCountPrempt() {
        return this.countPrempt;
    }

    public void intCountPrempt() {
        this.countPrempt = 1 + countPrempt;
    }

    public int getExitTime() {
        return this.exitTime;
    }

    public void setExitTime(int terminateTime) {
        this.exitTime = terminateTime;
    }

    public ProcessState getState() {
        return this.state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Burst getBurst() {
        return this.burst;
    }

    public void setBurst(Burst burst) {
        this.burst = burst;
    }

    public Queue<Burst> getQueueBursts() {
        return this.queueBursts;
    }

    public void setQueueBursts(Queue<Burst> queueBursts) {
        this.queueBursts = queueBursts;
    }

}