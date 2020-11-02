import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class RAM extends Thread {

	// Attributes
    private static final int 			MaxSize = 1073741824;					// the maximum size of the RAM memory (in Bytes).
    private static int 					size;									// Stores the current memory consumption.
    private static PriorityQueue<PCB> 	readyQueue = new PriorityQueue<PCB>(); 	// Currently allocated processes (We used SRTF).
    private static Queue<PCB> 			waitingQueue = new LinkedList<PCB>(); 	// Processes in waiting to be allocated memory.

    // Constructor
    public RAM() {
        size = 0;
        readyQueue = new PriorityQueue<PCB>();
        waitingQueue = new LinkedList<PCB>();
    }

    // Methods
    synchronized static void handleMemoryValue(PCB process, int neededMemory) {
		
    	int newSize = process.getSize() + neededMemory;
		if(newSize < 0) {
			// Kill process
			process.kill();
			return;
		}
		// Check if enough RAM is available
		if( !(RAM.size + process.getSize() >= MaxSize) ) {
			process.setToWaiting();
			RAM.size += neededMemory;
		}
		// Change process size to newSize
		process.setSize(newSize);
	}
    
    public static void addReadyQueue(PCB process) {
        readyQueue.add(process);
    }

    // Only allocates if 85% of the RAM Memory is not surpassed.
    public static boolean addWaitingQueue(PCB process) { 
    	
        if ( size + process.getSize() >= MaxSize * 0.85 ) {
            waitingQueue.add(process);
            return true;
        }
        return false;
    }
    
    // Setters and Getters.
    public static PCB getReadyQueue() {
        return readyQueue.poll();
    }

    public static PCB getWaitingQueue() {
        return waitingQueue.poll();
    }

    public static void addSize(int Size) {
        RAM.size += Size;
    }

    public static void removeSize(int Size) {
        RAM.size -= Size;
    }

    public static void setSize(final int Size) {
        RAM.size = Size;
    }

    public static int getMaxSize() {
        return RAM.MaxSize;
    }

    public static int getSize() {
        return RAM.size;
    }

}
