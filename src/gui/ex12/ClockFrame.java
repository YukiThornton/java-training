package gui.ex12;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ClockFrame extends Frame {

	private ClockCanvas canvas;
	private PreferenceDialog preferenceDialog;

	public ClockCanvas getCanvas() {
		return canvas;
	}
	
	public static ClockFrame getClockFrame(String title) {
		ClockFrame frame = new ClockFrame(title);
		frame.setup();
		return frame;
	}
	
	private ClockFrame(String title) {
		super(title);
	}
	
	private void setup() {
		setSize(Constants.DEFAULT_FRAME_SIZE);
		setMinimumSize(Constants.MINIMUN_FRAME_SIZE);
		setBackground(new Color(230, 225, 225));
		setLayout(new GridLayout());
		
		addWindowListener(new ClockWindowAdaptor());
		
		canvas = new ClockCanvas();
		add(canvas);
		preferenceDialog = PreferenceDialog.getPreferenceDialog(this);
		ClockMenu clockMenu = new ClockMenu();
		clockMenu.setup();
		setMenuBar(clockMenu);
	}
	
	private Rectangle getFrameLocation() {
		return getBounds();
	}
	
	private void changeFrameSize(double width, double height) {
		Rectangle rectangle = getBounds();
		setBounds(rectangle.x, rectangle.y, (int) width, (int)height);
	}
	
	private void autofill() {
		double widthRatio = (getBounds().getWidth() - 20) / canvas.getDateTextWidth();
		double heightRatio = (getBounds().getHeight() - 20) / canvas.getTotalTextHeight();
		if (widthRatio < heightRatio) {
			preferenceDialog.changeFontSize((int)(canvas.getFontSize() * widthRatio));
		} else {
			preferenceDialog.changeFontSize((int)(canvas.getFontSize() * heightRatio));
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
	
	class ClockMenu extends MenuBar implements ActionListener {
		
		private final String MENU_PREFERENCE = "Preference";
		private final String MENU_VIEW = "View";
		private final String MENU_ZOOM_IN = "Zoom in";
		private final String MENU_ZOOM_OUT = "Zoom out";
		private final String MENU_AUTOFIT_TEXT = "Autofit to Text";
		private final String MENU_AUTOFILL_WINDOW = "Autofill Window";
		
		public void setup() {
			Menu menuView = new Menu(MENU_VIEW);
			menuView.addActionListener(this);
			add(menuView);

			MenuItem menuItemPreference = new MenuItem(MENU_PREFERENCE);
			menuItemPreference.addActionListener(this);
			menuView.add(menuItemPreference);
			MenuItem menuItemZoomIn = new MenuItem(MENU_ZOOM_IN, new MenuShortcut(KeyEvent.VK_UP));
			menuItemZoomIn.addActionListener(this);
			menuView.add(menuItemZoomIn);
			MenuItem menuItemZoomOut = new MenuItem(MENU_ZOOM_OUT, new MenuShortcut(KeyEvent.VK_DOWN));
			menuItemZoomOut.addActionListener(this);
			menuView.add(menuItemZoomOut);
			MenuItem menuItemAutofit = new MenuItem(MENU_AUTOFIT_TEXT, new MenuShortcut(KeyEvent.VK_T));
			menuItemAutofit.addActionListener(this);
			menuView.add(menuItemAutofit);
			MenuItem menuItemAutofill = new MenuItem(MENU_AUTOFILL_WINDOW, new MenuShortcut(KeyEvent.VK_W));
			menuItemAutofill.addActionListener(this);
			menuView.add(menuItemAutofill);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case MENU_PREFERENCE:
				Rectangle frameLocation = getFrameLocation();
				int x;
				if (frameLocation.x > Constants.FIXED_DIALOG_SIZE.width) {
					x = frameLocation.x - Constants.FIXED_DIALOG_SIZE.width + 30;
				} else {
					x = frameLocation.x + frameLocation.width - 30;
				}
				preferenceDialog.setLocation(x, frameLocation.y);
				preferenceDialog.setVisible(true);
				break;

			case MENU_ZOOM_IN:
				preferenceDialog.enlargeTextFont();
				break;
				
			case MENU_ZOOM_OUT:
				preferenceDialog.shrinkTextFont();
				break;
				
			case MENU_AUTOFIT_TEXT:
				changeFrameSize(canvas.getDateTextWidth() + 20, canvas.getTotalTextHeight() + 20);
				break;
				
			case MENU_AUTOFILL_WINDOW:
				autofill();
				break;
				
			default:
				System.out.println(e.getActionCommand());
				break;
			}
		}
	}
}
