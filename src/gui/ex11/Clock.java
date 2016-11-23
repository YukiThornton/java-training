package gui.ex11;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Clock {
	
	public static void main(String[] args) {
		start();
	}
	
	protected static void start() {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);		
	}

	protected static class MyFrame extends Frame {
		public MyFrame() {
			super("Clock");
			setSize(400, 300);
			addWindowListener(new MyWindowAdaptor());
		}
	}

	protected static class MyWindowAdaptor extends WindowAdapter {
		public void windowOpened(WindowEvent event) {
			System.out.println("Opened a window.");		
		}
		public void windowClosing(WindowEvent event) {
			System.out.println("Closing the window.");
			System.exit(0);
		}
	}
}

