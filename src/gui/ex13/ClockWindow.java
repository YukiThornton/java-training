package gui.ex13;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Timer;

@SuppressWarnings("serial")
public class ClockWindow extends Window{

	private final static Font DEFAULT_FONT = new Font("arial black", Font.PLAIN, 24);
	private final static Color DEFAULT_BACKGROUND_COLOR = new Color(230, 225, 225);
	private final static Color DEFAULT_TEXT_COLOR_MAIN = new Color(77, 49, 49);
	private final static Color DEFAULT_TEXT_COLOR_FOOTER = new Color(112, 107, 107);
	private final static Color DEFAULT_TEXT_COLOR_SIDE = new Color(192, 108, 12);

	private ClockPopupMenu clockPopupMenu;
	
	private Image imgBuffer;
	private Graphics graphicBuffer;
	private LocalDateTime now;
	private TextData dateText;
	private TextData periodText;

	private String fontFamily = DEFAULT_FONT.getName();
	private int fontStyle = DEFAULT_FONT.getStyle();
	private int baseFontSize = DEFAULT_FONT.getSize();
	private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;
	private Color textColorMain = DEFAULT_TEXT_COLOR_MAIN;
	private Color textColorFooter = DEFAULT_TEXT_COLOR_FOOTER;
	private Color textColorSide = DEFAULT_TEXT_COLOR_SIDE;
	
	public ClockWindow(Window owner) {
		super(owner);
		MouseController mouseController = new MouseController();
		addMouseListener(mouseController);
		addMouseMotionListener(mouseController);
		clockPopupMenu = new ClockPopupMenu();
		add(clockPopupMenu);
	}
	
	public void paint(Graphics graphics) {
		Dimension dimension = getSize();
		if (imgBuffer != null && now.getSecond() == LocalDateTime.now().getSecond()) {
			return;
		}
		
		imgBuffer = createImage(dimension.width, dimension.height);
		graphicBuffer = imgBuffer.getGraphics();
		
		graphicBuffer.setColor(backgroundColor);
		graphicBuffer.fillRect(0, 0, dimension.width, dimension.height);
		
		now = LocalDateTime.now();
		
		Font mainFont = new Font(fontFamily, fontStyle, baseFontSize * 2);
		Font sideFont = new Font(fontFamily, fontStyle, baseFontSize);
		Font footerFont = new Font(fontFamily, fontStyle, baseFontSize);

		Point basePoint = new Point(dimension.width / 2, dimension.height / 2);
		//TextData center = new TextData("a", sideFont, baseTextColor, graphicBuffer);
		//center.locate(basePoint);
		//center.draw();		
		
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
		periodText.locate(secondText.x(), hourText.y() - secondText.height()/2 - (5 * baseFontSize / DEFAULT_FONT.getSize()));
		periodText.draw();
		
		// footer[EEEE - MMMM d]
		dateText = new TextData(now.format(DateTimeFormatter.ofPattern("EEEE - MMMM d", Locale.ENGLISH)), footerFont, textColorFooter, graphicBuffer);
		dateText.locate(basePoint.x - (dateText.width() / 2), hourText.y() + (int)(30 * baseFontSize / DEFAULT_FONT.getSize()));
		dateText.draw();
	
		graphics.drawImage(imgBuffer, 0, 0, this);		
	}

	public void update(Graphics g) {
		paint(g);
	}
	
	class MouseController implements MouseListener, MouseMotionListener {

		private Point mouseClickedPoint;
		
		private final static int MOUSE_BUTTON_ID_LEFT = 1;
		private final static int MOUSE_BUTTON_ID_RIGHT = 3;

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked");
			if (e.getButton() == MOUSE_BUTTON_ID_RIGHT) {
				System.out.println("popup");
				clockPopupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed");
			mouseClickedPoint = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased");
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouseDragged");
			Point point = e.getLocationOnScreen();
			Component component = e.getComponent();
			component.setLocation((int)(point.getX() - mouseClickedPoint.getX()), (int)(point.getY() - mouseClickedPoint.getY()));			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("mouseMoved");
		}
		

	}
	
	class ClockPopupMenu extends PopupMenu implements ActionListener {
		private static final String MENU_LABEL_BG_COLOR = "Background Color";
		private static final String MENU_LABEL_TXT_COLOR = "Text Color";
		private static final String MENU_LABEL_TXT_FONT = "Text Font";
		private static final String MENU_LABEL_TXT_SIZE = "Text Size";
		private static final String MENU_LABEL_CLOSE = "Close";

		ClockPopupMenu() {
			MenuItem menuBgColor = new MenuItem(MENU_LABEL_BG_COLOR);
			menuBgColor.addActionListener(this);
			add(menuBgColor);
			MenuItem menuTxtColor = new MenuItem(MENU_LABEL_TXT_COLOR);
			menuTxtColor.addActionListener(this);
			add(menuTxtColor);
			MenuItem menuTxtFont = new MenuItem(MENU_LABEL_TXT_FONT);
			menuTxtFont.addActionListener(this);
			add(menuTxtFont);
			MenuItem menuTxtSize = new MenuItem(MENU_LABEL_TXT_SIZE);
			menuTxtSize.addActionListener(this);
			add(menuTxtSize);
			MenuItem menuClose = new MenuItem(MENU_LABEL_CLOSE);
			menuClose.addActionListener(this);
			add(menuClose);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case MENU_LABEL_BG_COLOR:
				System.out.println("MENU_LABEL_BG_COLOR");
				break;

			case MENU_LABEL_TXT_COLOR:
				System.out.println("MENU_LABEL_TXT_COLOR");
				break;

			case MENU_LABEL_TXT_FONT:
				System.out.println("MENU_LABEL_TXT_FONT");
				break;

			case MENU_LABEL_TXT_SIZE:
				System.out.println("MENU_LABEL_TXT_SIZE");
				break;

			case MENU_LABEL_CLOSE:
				System.exit(0);
				break;

			default:
				System.out.println("actionPerformed: " + e.getActionCommand());
				break;
			}
		}
		
	}
}
