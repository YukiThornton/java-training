package gui.ex12;

public class Clock{
	
	private Thread thread;
	private ClockFrame frame;
	private final static String CLOCK_TITLE = "Clock";
	
	public ClockFrame getFrame() {
		return frame;
	}

	public Clock() {
		frame = ClockFrame.getClockFrame(CLOCK_TITLE);
		thread = new Thread(frame.getCanvas());
	}
	
	public void start() {
		thread.start();
		frame.setVisible(true);		
	}
	
	public static void main(String[] args) {
		Clock clock = new Clock();
		clock.start();
	}
}

