
public class CPUBurst extends Burst {
	
    // Attributes.
    private int memory;		// The amount of memory allocated to this process.

    // Constructor.
    public CPUBurst(int burst, int memory) {
        this.burst = burst;
        this.memory = memory;
    }

    // Methods.
    public void setBurst(int burst) {
        this.burst = burst;
    }
    
    public boolean decBurst() {
        return --burst == 0;
    }
    
    public int getBurst() {
        return burst;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getMemory() {
        return this.memory;
    }

}



