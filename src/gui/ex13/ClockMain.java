package gui.ex13;

import java.awt.Dimension;

public class ClockMain implements Runnable{

	private static ClockWindow window;

	private final static Dimension DEFAULT_FRAME_SIZE = new Dimension(1, 1);
	private final static int PAINT_INTERVAL = 100;
	
	public static void main(String[] args) {
		Thread thread = new Thread(new ClockMain());
		
		window = new ClockWindow(null);
		window.setSize(DEFAULT_FRAME_SIZE);

        window.setVisible(true);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(PAINT_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			window.repaint();
		}
	}

}
