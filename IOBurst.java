
public class IOBurst extends Burst {

	// Constructor.
	public IOBurst(int burst) {
        this.burst = burst;
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
    

}