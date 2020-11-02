

public abstract class Burst {
	
	// Attributes
    protected int  burst; 	// Number of CPU/IO bursts needed to finish executing.

    // Methods.
    // Reduces the value of 'burst' by 1 then returns true if 'burst' equals 0 or false otherwise.
    public abstract boolean decBurst();
    
    // Setters and getters
    public abstract int getBurst();

    public abstract void setBurst(int burst);

}
