package gui.ex14;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SuppressWarnings("serial")
public class ClockFrame extends Frame {

    private final ClockPreference clockPreference;
    
    private Rectangle frameRect;
    
    private String fontFamily;
    private int fontStyle;
    private int baseFontSize;
    private ClockColor backgroundColor;
    private ClockColor textColorMain;
    private ClockColor textColorFooter;
    private ClockColor textColorSide;
    
	private PreferenceDialog preferenceDialog;
    private Image imgBuffer;
    private Graphics graphicBuffer;
    private LocalDateTime now;
    
    private TextData dateText;
    private TextData periodText;
	
	private final int CANVAS_MARGIN_WIDTH = 80;
	private final int CANVAS_MARGIN_HEIGHT = 80;
	
	
	public ClockFrame(String title, ClockPreference clockPreference) {
		super(title);
		this.clockPreference = clockPreference;
		setup();
	}
	
	private void setup() {
	    checkPreferenceChange();
	
		setBounds(frameRect);
		setMinimumSize(Constants.MINIMUN_FRAME_SIZE);
		setBackground(Color.WHITE);
		setLayout(new GridLayout());
		
		addWindowListener(new ClockWindowAdaptor());
		addComponentListener(new ClockComponentListener());
		
		ClockMenu clockMenu = new ClockMenu();
		clockMenu.setup();
		setMenuBar(clockMenu);
	}
	
	private void checkPreferenceChange(){
	    frameRect = clockPreference.getFramePosition();
	
        fontFamily = clockPreference.getSelectedTxtFont().getName();
        fontStyle = clockPreference.getSelectedTxtFont().getStyle();
        baseFontSize = clockPreference.getSelectedTxtSize();
        backgroundColor = clockPreference.getSelectedBgColor();
        textColorMain = clockPreference.getSelectedMainTxtColor();
        textColorFooter = clockPreference.getSelectedFooterTxtColor();
        textColorSide = clockPreference.getSelectedSideTxtColor();
	}
	
	public void absorbPreferenceChange() {
        checkPreferenceChange();
        repaint();
    }
	
	private Rectangle getFrameLocation() {
		return getBounds();
	}
	
    public double getDateTextWidth() {
        return dateText.width();
    }
    
    public double getTotalTextHeight() {
        return (dateText.y() - periodText.y()) + dateText.height();
    }
    
	private void autofit() {
		Rectangle rectangle = getBounds();
		setBounds(rectangle.x, rectangle.y, (int)getDateTextWidth() + CANVAS_MARGIN_WIDTH, (int)getTotalTextHeight() + CANVAS_MARGIN_HEIGHT);
	}
	
	private void autofill() {
		double widthRatio = (getBounds().getWidth() - CANVAS_MARGIN_WIDTH) / getDateTextWidth();
		double heightRatio = (getBounds().getHeight() - CANVAS_MARGIN_HEIGHT) / getTotalTextHeight();
		int index = 0;
		if (widthRatio < heightRatio) {
		    index = clockPreference.getClosestTxtSizeIndex((int)(baseFontSize * widthRatio));
		} else {
            index = clockPreference.getClosestTxtSizeIndex((int)(baseFontSize * heightRatio));
		}
        clockPreference.setTxtSize(index);
        baseFontSize = clockPreference.getSelectedTxtSize();
        repaint();
	}
    
    private void adjustTxtSize(int level) {
        clockPreference.setTxtSize(clockPreference.getSelectedTxtSizeIndex() + level);
        baseFontSize = clockPreference.getSelectedTxtSize();
        repaint();
    }
    
    public void paint(Graphics graphics) {
        Dimension dimension = getSize();
        if (imgBuffer != null && imgBuffer.getWidth(this) == dimension.width && imgBuffer.getHeight(this) == dimension.height && now.getSecond() == LocalDateTime.now().getSecond()) {
            return;
        }
        
        imgBuffer = createImage(dimension.width, dimension.height);
        graphicBuffer = imgBuffer.getGraphics();
        
        graphicBuffer.setColor(backgroundColor);
        setBackground(backgroundColor);
        graphicBuffer.fillRect(0, 0, dimension.width, dimension.height);
        
        now = LocalDateTime.now();
        
        Font mainFont = new Font(fontFamily, fontStyle, baseFontSize * 2);
        Font sideFont = new Font(fontFamily, fontStyle, baseFontSize);
        Font footerFont = new Font(fontFamily, fontStyle, baseFontSize);

        Point basePoint = new Point(dimension.width / 2, dimension.height / 2);
        
        // main [hh:mm]
        String hourString = now.format(DateTimeFormatter.ofPattern("hh:mm"));
        TextData hourText = new TextData(hourString, mainFont, textColorMain, graphicBuffer);
        TextData hourTextWithSpace = new TextData(hourString + " ", mainFont, textColorMain, graphicBuffer);
        hourText.locate(basePoint.x - (hourTextWithSpace.width() / 2), basePoint.y);
        hourText.draw();

        // side [ss]
        TextData secondText = new TextData(now.format(DateTimeFormatter.ofPattern("ss")), sideFont, textColorMain, graphicBuffer);
        secondText.locate(hourText.x() + hourText.width(), hourText.y());
        secondText.draw();
        
        // side[a]
        periodText = new TextData(now.format(DateTimeFormatter.ofPattern("a", Locale.ENGLISH)), sideFont, textColorSide, graphicBuffer);
        periodText.locate(secondText.x(), hourText.y() - secondText.height()/2 - (5 * baseFontSize / Constants.DEFAULT_FONT.getSize()));
        periodText.draw();
        
        // footer[EEEE - MMMM d]
        dateText = new TextData(now.format(DateTimeFormatter.ofPattern("EEEE - MMMM d", Locale.ENGLISH)), footerFont, textColorFooter, graphicBuffer);
        dateText.locate(basePoint.x - (dateText.width() / 2), hourText.y() + (int)(30 * baseFontSize / Constants.DEFAULT_FONT.getSize()));
        dateText.draw();
    
        graphics.drawImage(imgBuffer, 0, 0, this);      
    }
    
    public void update(Graphics g) {
        paint(g);
    }
    
    private void createPreferenceDialog(){
        preferenceDialog = new PreferenceDialog(this, clockPreference);
        Rectangle frameLocation = getFrameLocation();
        int x;
        if (frameLocation.x > Constants.FIXED_DIALOG_SIZE.width) {
            x = frameLocation.x - Constants.FIXED_DIALOG_SIZE.width + 30;
        } else {
            x = frameLocation.x + frameLocation.width - 30;
        }
        preferenceDialog.setLocation(x, frameLocation.y);
        preferenceDialog.setVisible(true);
    }
    
	class ClockComponentListener extends ComponentAdapter {
		public void componentResized(ComponentEvent event) {
		    repaint();
		}
		
	}
	
	class ClockWindowAdaptor extends WindowAdapter {
		public void windowOpened(WindowEvent event) {
			System.out.println("Opened a window.");		
		}
		public void windowClosing(WindowEvent event) {
			System.out.println("Closing the window.");
			clockPreference.setFramePosition(getBounds());
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

			MenuItem menuItemPreference = new MenuItem(MENU_PREFERENCE, new MenuShortcut(KeyEvent.VK_P));
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
			    createPreferenceDialog();
				break;

			case MENU_ZOOM_IN:
			    adjustTxtSize(1);
				break;
				
			case MENU_ZOOM_OUT:
			    adjustTxtSize(-1);
				break;
				
			case MENU_AUTOFIT_TEXT:
				autofit();
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
