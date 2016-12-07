package gui.ex12;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClockFrame extends Frame implements ActionListener {

	private final static String TITLE = "Clock";
	private final String MENU_PREFERENCE = "Preference";
	private final String MENU_VIEW = "View";
	private Thread thread;
	private ClockCanvas clockCanvas;
	
	public ClockFrame() {
		super(TITLE);
		setup();
	}
	
	protected void setup() {
		clockCanvas = new ClockCanvas();
		thread = new Thread(clockCanvas);
		
		setSize(Constants.DEFAULT_FRAME_SIZE);
		setMinimumSize(Constants.MINIMUN_FRAME_SIZE);
		setBackground(new Color(230, 225, 225));
		addWindowListener(new ClockWindowAdaptor());
		setMenuBar(menuBar());
		setLayout(new GridLayout());
		add(clockCanvas);
	}
	
	public void start() {
		thread.start();		
		setVisible(true);
	}
	
	protected MenuBar menuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menuView = new Menu(MENU_VIEW);
		menuView.addActionListener(this);
		MenuItem menuItemPreference = new MenuItem(MENU_PREFERENCE);
		menuItemPreference.addActionListener(this);
		
		menuBar.add(menuView);
		menuView.add(menuItemPreference);
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case MENU_PREFERENCE:
			System.out.println("nex");
			PreferenceDialog preferenceDialog = new PreferenceDialog(this);
			preferenceDialog.setVisible(true);
			break;

		default:
			System.out.println(e.getActionCommand());
			break;
		}
	}
	
	class ClockWindowAdaptor extends WindowAdapter {
		public void windowOpened(WindowEvent event) {
			System.out.println("Opened a window.");		
		}
		public void windowClosing(WindowEvent event) {
			System.out.println("Closing the window.");
			System.exit(0);
		}
	}
	
	void changeFont(Font font) {
		clockCanvas.changeFont(font);
	}
	void changeFontSize (int size) {
		clockCanvas.changeFontSize(size);
	}
	
	void changeBackGroundColor(Color color) {
		clockCanvas.changeBackgroundColor(color);
	}
	
	void changeTextMainColor(Color color) {
		clockCanvas.changeMainTextColor(color);
	}
	
	void changeTextSideColor(Color color) {
		clockCanvas.changeSideTextColor(color);
	}
	
	void changeTextFooterColor(Color color) {
		clockCanvas.changeFooterTextColor(color);
	}
	
}
