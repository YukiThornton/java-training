package gui.ex13;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
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

@SuppressWarnings("serial")
public class ClockWindow extends Window{

    private final int WINDOW_MARGIN_WIDTH = 50;
    private final int WINDOW_MARGIN_HEIGHT = 30;
    
    private static final ClockColor WHITE = new ClockColor("White", 255, 255, 255);
    private static final ClockColor CREAM = new ClockColor("Cream", 230, 225, 225);
    private static final ClockColor YELLOW = new ClockColor("Yellow", 255, 215, 0);
    private static final ClockColor BURNT_ORANGE = new ClockColor("Dark Orange", 192, 108, 12);
    private static final ClockColor RED = new ClockColor("Red", 255, 51, 51);
    private static final ClockColor BROWN = new ClockColor("Brown",139, 69, 19);
    private static final ClockColor DARK_BROWN = new ClockColor("Dark Brown", 77, 49, 49);
    private static final ClockColor MINT_GREEN = new ClockColor("Mint Green", 127, 255, 212);
    private static final ClockColor GREEN = new ClockColor("Green", 0, 153, 51);
    private static final ClockColor BLUE = new ClockColor("Blue",30, 144, 255);
    private static final ClockColor LIGHT_GRAY = new ClockColor("Light Gray", 211, 211, 211);
    private static final ClockColor DARK_GRAY = new ClockColor("Dark Gray", 105, 105, 105);
    private static final ClockColor BLACK = new ClockColor("Black", 0, 0, 0);
    
    private static final Font FONT_ARIAL_BLACK = new Font("Arial Black", Font.PLAIN, 24);
    private static final Font FONT_IMPACT = new Font("Impact", Font.PLAIN, 24);
    private static final Font FONT_GEORGIA = new Font("Georgia", Font.PLAIN, 24);
    private static final Font FONT_COUERIER = new Font("Courier New", Font.BOLD, 24);
    private static final Font FONT_NEW_ROMAN = new Font("Times New Roman", Font.PLAIN, 24);
    private static final Font FONT_TREBUCHET = new Font("Trebuchet MS", Font.PLAIN, 24);
    private static final Font FONT_VERDANA = new Font("Verdana", Font.PLAIN, 24);
    private static final Font FONT_COMIC_SANS = new Font("Comic Sans MS", Font.PLAIN, 24);
    
    private static final ClockTheme THEME_CALM = new ClockTheme("Calm", CREAM, DARK_BROWN, DARK_GRAY, BURNT_ORANGE, FONT_ARIAL_BLACK);
    private static final ClockTheme THEME_SIMPLE = new ClockTheme("Simple", WHITE, BLACK, DARK_GRAY, RED, FONT_VERDANA);
    private static final ClockTheme THEME_DARK = new ClockTheme("Dark", BLACK, LIGHT_GRAY, DARK_GRAY, DARK_GRAY, FONT_IMPACT);
    private static final ClockTheme THEME_CHOCO = new ClockTheme("Mint Chocolate", MINT_GREEN, DARK_BROWN, DARK_BROWN, BROWN, FONT_TREBUCHET);

    private static final ClockTheme[] THEME_OPTIONS = new ClockTheme[]{THEME_CALM, THEME_SIMPLE, THEME_DARK, THEME_CHOCO};
    private static final ClockColor[] COLOR_OPTIONS = new ClockColor[] {WHITE, CREAM, YELLOW, BURNT_ORANGE, RED, BROWN, DARK_BROWN, MINT_GREEN, GREEN, BLUE, LIGHT_GRAY, DARK_GRAY, BLACK};
    private static final Font[] FONT_OPTIONS = new Font[]{FONT_ARIAL_BLACK, FONT_IMPACT, FONT_GEORGIA, FONT_COUERIER, FONT_NEW_ROMAN, FONT_TREBUCHET, FONT_VERDANA, FONT_COMIC_SANS};
    private static final int[] TXT_SIZE_OPTIONS = new int[]{5, 10, 12, 16, 20, 24, 28, 36, 48, 60, 80, 100, 120, 150, 180, 200};
    
    private final static ClockTheme DEFAULT_THEME = THEME_CALM;
    
	private ClockPopupMenu clockPopupMenu;
	
	private Image imgBuffer;
	private Graphics graphicBuffer;
	private LocalDateTime now;
	private TextData dateText;
	private TextData periodText;
	private Dimension originalDimension;
	private boolean isPaintedBefore = false;
	private boolean dimensionChanged = false;
	private boolean propertyChanged = false;

	private String fontFamily = DEFAULT_THEME.txtFont().getName();
	private int fontStyle = DEFAULT_THEME.txtFont().getStyle();
	private int baseFontSize = DEFAULT_THEME.txtFont().getSize();
	private Color backgroundColor = DEFAULT_THEME.bgColor();
	private Color textColorMain = DEFAULT_THEME.mainTxtColor();
	private Color textColorFooter = DEFAULT_THEME.footerTxtColor();
	private Color textColorSide = DEFAULT_THEME.sideTxtColor();
	
	public ClockWindow(Window owner) {
		super(owner);
		MouseController mouseController = new MouseController();
		addMouseListener(mouseController);
		addMouseMotionListener(mouseController);
		clockPopupMenu = new ClockPopupMenu();
		add(clockPopupMenu);
	}
	
	public void paint(Graphics graphics) {
        if (imgBuffer != null && now.getSecond() == LocalDateTime.now().getSecond() && !propertyChanged) {
            return;
        }
        Dimension dimension = getSize();
        now = LocalDateTime.now();
        if (!isPaintedBefore || dimensionChanged) {
            imgBuffer = createImage(dimension.width, dimension.height);
            graphicBuffer = imgBuffer.getGraphics();
            paintTxt(graphicBuffer, dimension);
            if(!isPaintedBefore){
                originalDimension = new Dimension(getDateTextWidth(), getTotalTextHeight());
            }
            int widthMargin = (int)(WINDOW_MARGIN_WIDTH * (getDateTextWidth() / originalDimension.getWidth()));
            int heightMargin = (int)(WINDOW_MARGIN_HEIGHT * (getTotalTextHeight() / originalDimension.getHeight()));
            dimension = new Dimension(getDateTextWidth() + widthMargin, getTotalTextHeight() + heightMargin);
            isPaintedBefore = true;
            dimensionChanged = false;
        }
        
		imgBuffer = createImage(dimension.width, dimension.height);
		graphicBuffer = imgBuffer.getGraphics();
		
		paintTxt(graphicBuffer, dimension);
        setBounds(getBounds().x, getBounds().y, dimension.width, dimension.height);
		graphics.drawImage(imgBuffer, 0, 0, this);
        if(propertyChanged) {
            bringToFront();
            propertyChanged = false;
        }

	}
	
	private void paintTxt(Graphics graphics, Dimension dimension) {
        Font mainFont = new Font(fontFamily, fontStyle, baseFontSize * 2);
        Font sideFont = new Font(fontFamily, fontStyle, baseFontSize);
        Font footerFont = new Font(fontFamily, fontStyle, baseFontSize);

        Point basePoint = new Point(dimension.width / 2, dimension.height / 2);
        
        // main [hh:mm]
        String hourString = now.format(DateTimeFormatter.ofPattern("hh:mm"));
        TextData hourText = new TextData(hourString, mainFont, textColorMain, graphics);
        TextData hourTextWithSpace = new TextData(hourString + " ", mainFont, textColorMain, graphics);
        hourText.locate(basePoint.x - (hourTextWithSpace.width() / 2), basePoint.y);
        hourText.draw();

        // side [ss]
        TextData secondText = new TextData(now.format(DateTimeFormatter.ofPattern("ss")), sideFont, textColorMain, graphics);
        secondText.locate(hourText.x() + hourText.width(), hourText.y());
        secondText.draw();
        
        // side[a]
        periodText = new TextData(now.format(DateTimeFormatter.ofPattern("a", Locale.ENGLISH)), sideFont, textColorSide, graphics);
        periodText.locate(secondText.x(), hourText.y() - secondText.height()/2 - (5 * baseFontSize / DEFAULT_THEME.txtFont().getSize()));
        periodText.draw();
        
        // footer[EEEE - MMMM d]
        dateText = new TextData(now.format(DateTimeFormatter.ofPattern("EEEE - MMMM d", Locale.ENGLISH)), footerFont, textColorFooter, graphics);
        dateText.locate(basePoint.x - (dateText.width() / 2), hourText.y() + (int)(30 * baseFontSize / DEFAULT_THEME.txtFont().getSize()));
        dateText.draw();
	}

	public void update(Graphics g) {
		paint(g);
	}

    private int getDateTextWidth() {
        return (int)dateText.width();
    }
    
    private int getTotalTextHeight() {
        return (int)((dateText.y() - periodText.y()) + dateText.height());
    }
    
    private void bringToFront() {
        toFront();
        setAlwaysOnTop(true);
        requestFocus();
        setAlwaysOnTop(false);
    }
    	
	class MouseController implements MouseListener, MouseMotionListener {

		private Point mouseClickedPoint;
		
		//private final static int MOUSE_BUTTON_ID_LEFT = 1;
		private final static int MOUSE_BUTTON_ID_RIGHT = 3;

		@Override
		public void mouseClicked(MouseEvent e) {
		    bringToFront();
			if (e.getButton() == MOUSE_BUTTON_ID_RIGHT) {
				clockPopupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
            bringToFront();
			mouseClickedPoint = e.getPoint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			Point point = e.getLocationOnScreen();
			Component component = e.getComponent();
			component.setLocation((int)(point.getX() - mouseClickedPoint.getX()), (int)(point.getY() - mouseClickedPoint.getY()));			
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
		

	}
	
	class ClockPopupMenu extends PopupMenu implements ActionListener {
        private static final String MENU_LABEL_THEMES = "Themes";
        private static final String MENU_LABEL_BG_COLOR = "Background Color";
        private static final String MENU_LABEL_TXT_COLOR = "Text Color";
        private static final String MENU_LABEL_TXT_TIME = "Time";
        private static final String MENU_LABEL_TXT_DATE = "Date";
        private static final String MENU_LABEL_TXT_AMPM = "AM/PM";
        private static final String MENU_LABEL_TXT_FONT = "Text Font";
        private static final String MENU_LABEL_TXT_SIZE = "Text Size";
        private static final String MENU_LABEL_CLOSE = "Close";
        
        Menu menuThemes;
        Menu menuBgColors;
        Menu menuTxtColors;
        Menu menuMainTxtColors;
        Menu menuFooterTxtColors;
        Menu menuSideTxtColors;
        Menu menuTxtFonts;
        Menu menuTxtSizes;

		ClockPopupMenu() {
            menuThemes = new Menu(MENU_LABEL_THEMES);
            for (ClockTheme theme: THEME_OPTIONS) {
                MenuItem menuTheme = new MenuItem(theme.label());
                menuTheme.addActionListener(this);
                menuThemes.add(menuTheme);
            }
            add(menuThemes);
            addSeparator();
            
			menuBgColors = new Menu(MENU_LABEL_BG_COLOR);
			for(ClockColor color: COLOR_OPTIONS) {
	            MenuItem menuBgColor = new MenuItem(color.label());
	            menuBgColor.addActionListener(this);
	            menuBgColors.add(menuBgColor);
			}
			add(menuBgColors);
			
            menuTxtColors = new Menu(MENU_LABEL_TXT_COLOR);
			menuMainTxtColors = new Menu(MENU_LABEL_TXT_TIME);
            for(ClockColor color: COLOR_OPTIONS) {
                MenuItem menuMainTxtColor = new MenuItem(color.label());
                menuMainTxtColor.addActionListener(this);
                menuMainTxtColors.add(menuMainTxtColor);
            }
            menuTxtColors.add(menuMainTxtColors);
            menuFooterTxtColors = new Menu(MENU_LABEL_TXT_DATE);
            for(ClockColor color: COLOR_OPTIONS) {
                MenuItem menuFooterTxtColor = new MenuItem(color.label());
                menuFooterTxtColor.addActionListener(this);
                menuFooterTxtColors.add(menuFooterTxtColor);
            }
            menuTxtColors.add(menuFooterTxtColors);
            menuSideTxtColors = new Menu(MENU_LABEL_TXT_AMPM);
            for(ClockColor color: COLOR_OPTIONS) {
                MenuItem menuSideTxtColor = new MenuItem(color.label());
                menuSideTxtColor.addActionListener(this);
                menuSideTxtColors.add(menuSideTxtColor);
            }
            menuTxtColors.add(menuSideTxtColors);
			add(menuTxtColors);
			
			menuTxtFonts = new Menu(MENU_LABEL_TXT_FONT);
			for(Font font: FONT_OPTIONS) {
			    MenuItem menuTxtFont = new MenuItem(font.getName());
			    menuTxtFont.addActionListener(this);
			    menuTxtFonts.add(menuTxtFont);
			}
			add(menuTxtFonts);
			
			menuTxtSizes = new Menu(MENU_LABEL_TXT_SIZE);
			for(int txtSize: TXT_SIZE_OPTIONS){
			    MenuItem menuTxtSize = new MenuItem(Integer.toString(txtSize));
			    menuTxtSize.addActionListener(this);
			    menuTxtSizes.add(menuTxtSize);
			}
			add(menuTxtSizes);
			
            addSeparator();

			MenuItem menuClose = new MenuItem(MENU_LABEL_CLOSE);
			menuClose.addActionListener(this);
			add(menuClose);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		    MenuItem source = (MenuItem)e.getSource();
		    if (source.getParent().equals(menuThemes)) {
		        changeTheme(determineTheme(e.getActionCommand()));
            } else if (source.getParent().equals(menuBgColors)) {
                changeBgColor(determineColor(e.getActionCommand()));
            } else if (source.getParent().equals(menuMainTxtColors)) {
                changeMainTxtColor(determineColor(e.getActionCommand()));
            } else if (source.getParent().equals(menuFooterTxtColors)) {
                changeFooterTxtColor(determineColor(e.getActionCommand()));
            } else if (source.getParent().equals(menuSideTxtColors)) {
                changeSideTxtColor(determineColor(e.getActionCommand()));
            } else if (source.getParent().equals(menuTxtFonts)) {
                changeFont(determineFont(e.getActionCommand()));
            } else if (source.getParent().equals(menuTxtSizes)) {
                changeTxtSize(Integer.parseInt(e.getActionCommand()));
            } else if (e.getActionCommand().equals(MENU_LABEL_CLOSE)){
                System.exit(0);
            }
		}
		
		private ClockTheme determineTheme(String themeLabel) {
		    ClockTheme result = null;
		    for (ClockTheme theme : THEME_OPTIONS) {
                if (theme.label().equals(themeLabel)) {
                    result = theme;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException();
            }
            return result;
		}
		
		private ClockColor determineColor(String colorLabel){
		    ClockColor result = null;
		    for(ClockColor color: COLOR_OPTIONS) {
		        if(color.label().equals(colorLabel)){
		            result = color;
		        }
		    }
            if (result == null) {
                throw new IllegalArgumentException();
            }
            return result;
		}
		
        private Font determineFont(String fontLabel){
            Font result = null;
            for(Font font: FONT_OPTIONS) {
                if(font.getName().equals(fontLabel)){
                    result = font;
                }
            }
            if (result == null) {
                throw new IllegalArgumentException();
            }
            return result;
        }
        
		private void changeTheme(ClockTheme target) {
            changeBgColor(target.bgColor());
            changeMainTxtColor(target.mainTxtColor());
            changeFooterTxtColor(target.footerTxtColor());
            changeSideTxtColor(target.sideTxtColor());
            changeFont(target.txtFont());
        }
        
        private void changeBgColor(ClockColor target){
            backgroundColor = (Color)target;
            propertyChanged = true;
            setBackground(backgroundColor);
            repaint();
        }
        
        private void changeMainTxtColor(ClockColor target){
            textColorMain = (Color)target;
            propertyChanged = true;
            repaint();
        }
        
        private void changeFooterTxtColor(ClockColor target) {
            textColorFooter = (Color)target;
            propertyChanged = true;
            repaint();
        }
        
        private void changeSideTxtColor(ClockColor target){
            textColorSide = (Color)target;
            propertyChanged = true;
            repaint();
        }
        
        private void changeFont(Font target){
            fontFamily = target.getName();
            fontStyle = target.getStyle();
            dimensionChanged = true;
            propertyChanged = true;
            repaint();
        }
        
        private void changeTxtSize(int target) {
            baseFontSize = target;
            dimensionChanged = true;
            propertyChanged = true;
            repaint();
        }

	}
}
