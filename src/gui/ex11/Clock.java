package gui.ex11;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Clock {
	
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
	}

}

class MyFrame extends Frame {
	public MyFrame() {
		super();
		setTitle("Clock");
		setSize(400, 300);
		addWindowListener(new MyWindowAdaptor());
	}
}

class MyWindowAdaptor extends WindowAdapter {
	public void windowOpened(WindowEvent event) {
		System.out.println("Opened a window.");		
	}
	public void windowClosing(WindowEvent event) {
		System.out.println("Closing the window.");
		System.exit(0);
	}
}
