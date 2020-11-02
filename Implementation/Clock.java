
public class Clock {
	
	// Attributes.
    private static int time = 0;	// Time in milliseconds.

    // Methods.
    public static int getTime() {
        return Clock.time;
    }

    public static void incTime() {
        Clock.time += 1;
    }

    public static void incTimeBy(int x){
        Clock.time += x;
    }
    
}
