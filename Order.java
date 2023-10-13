package kodachi_CSCI201_Assignment2;

public class Order extends Thread{
    private int time;
    private String n;
    private String item;
    private Restaurant rest;
//    private DateTimeFormatter f = DateTimeFormatter.ofPattern("HH:mm:ss.SS");

    public Order(int t,String n,String i, Restaurant rest) {
        this.time = t;
        this.n = n;
        this.item = i;
        this.rest = rest;
    }
    
    public String getN() {
    	return this.n;
    }
    
    public String getItem() {
    	return this.item;
    }

    public void print() {
        System.out.println("Time: " + Integer.toString(this.time));
        System.out.println("Name: " + this.n);
        System.out.println("Restaurant: " + this.rest);
        System.out.println("Food: " + this.item);
        System.out.println("");
    }
    
	public String getTime(long ms) {
		// "convert time given in ms to format HH:mm:ss.SSS prompt (9 lines) ChatGPT 3 Aug. version, OpenAI, 25 Sep. 2023, chat.openai.com/chat.
		long hours = ms / 3600000;
	    long minutes = (ms % 3600000) / 60000;
	    long seconds = ((ms % 3600000) % 60000) / 1000;
	    long milliseconds = ms % 1000;
	    if (hours > 0) {
	        return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
	    } else {
	        return String.format("00:%02d:%02d.%03d", minutes, seconds, milliseconds);
	    }
	}

    public void run() {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException ie) {
            System.out.println("Order Interrupted");
        }
        
        try {
        	rest.sem.acquire();
        	long now = System.currentTimeMillis() - Restaurant.getStart();
//        	System.out.println((int)(rest.getDist() * 2 * 1000));
        	System.out.println("[" + this.getTime(now) + "] " + "Starting delivery of " + this.item + " from " + rest.getN() + "!");
        	Thread.sleep((int)(rest.getDist() * 2 * 1000));
        	now = System.currentTimeMillis() - Restaurant.getStart();
			System.out.println("[" + this.getTime(now) + "] " + "Finished delivery of " + this.item + " from " + rest.getN() + "!");
        } catch(InterruptedException ie) {
        	System.out.println("Order Interrupted");
        } finally {
        	rest.sem.release();
        }
    }
}