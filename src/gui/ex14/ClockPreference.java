package gui.ex14;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.prefs.Preferences;

public class ClockPreference {

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
    
    private final static Rectangle DEFAULT_FRAME_RECT = new Rectangle(0, 0, 400, 600);
    private final static int DEFAULT_THEME_INDEX = 0;
    private final static int DEFAULT_BG_COLOR_INDEX = 1;
    private final static int DEFAULT_MAIN_TXT_COLOR_INDEX = 6;
    private final static int DEFAULT_FOOTER_TXT_COLOR_INDEX = 11;
    private final static int DEFAULT_SIDE_TXT_COLOR_INDEX = 3;
    private final static int DEFAULT_TXT_FONT_INDEX = 0;
    private final static int DEFAULT_TXT_SIZE_INDEX = 5;
    
    private final static String PREF_KEY_FRAME_X = "okuno_clock_ex14_1.0_frame_x";
    private final static String PREF_KEY_FRAME_Y = "okuno_clock_ex14_1.0_frame_y";
    private final static String PREF_KEY_FRAME_WIDTH = "okuno_clock_ex14_1.0_frame_width";
    private final static String PREF_KEY_FRAME_HEIGHT = "okuno_clock_ex14_1.0_frame_height";
    private final static String PREF_KEY_THEME = "okuno_clock_ex14_1.0_theme";
    private final static String PREF_KEY_BG_COLOR = "okuno_clock_ex14_1.0_bgColor";
    private final static String PREF_KEY_MAIN_TXT_COLOR = "okuno_clock_ex14_1.0_mainTxtColor";
    private final static String PREF_KEY_FOOTER_TXT_COLOR = "okuno_clock_ex14_1.0_footerTxtColor";
    private final static String PREF_KEY_SIDE_TXT_COLOR = "okuno_clock_ex14_1.0_sideTxtColor";
    private final static String PREF_KEY_TXT_FONT = "okuno_clock_ex14_1.0_txtFont";
    private final static String PREF_KEY_TXT_SIZE = "okuno_clock_ex14_1.0_txtSize";
    
    private Preferences preferences;
    
    public ClockPreference() {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        
        preferences.getInt(PREF_KEY_FRAME_X, DEFAULT_FRAME_RECT.x);
        preferences.getInt(PREF_KEY_FRAME_Y, DEFAULT_FRAME_RECT.y);
        preferences.getInt(PREF_KEY_FRAME_WIDTH, DEFAULT_FRAME_RECT.width);
        preferences.getInt(PREF_KEY_FRAME_HEIGHT, DEFAULT_FRAME_RECT.height);
        
        preferences.getInt(PREF_KEY_BG_COLOR, DEFAULT_BG_COLOR_INDEX);
        preferences.getInt(PREF_KEY_MAIN_TXT_COLOR, DEFAULT_MAIN_TXT_COLOR_INDEX);
        preferences.getInt(PREF_KEY_FOOTER_TXT_COLOR, DEFAULT_FOOTER_TXT_COLOR_INDEX);
        preferences.getInt(PREF_KEY_SIDE_TXT_COLOR, DEFAULT_SIDE_TXT_COLOR_INDEX);
        preferences.getInt(PREF_KEY_TXT_FONT, DEFAULT_TXT_FONT_INDEX);
        preferences.getInt(PREF_KEY_TXT_SIZE, DEFAULT_TXT_SIZE_INDEX);
    }

    public static ClockTheme[] getThemeOptions() {
        return THEME_OPTIONS;
    }

    public static ClockColor[] getColorOptions() {
        return COLOR_OPTIONS;
    }

    public static Font[] getFontOptions() {
        return FONT_OPTIONS;
    }

    public static int[] getTxtSizeOptions() {
        return TXT_SIZE_OPTIONS;
    }
    
    public Rectangle getFramePosition() {
        int x = preferences.getInt(PREF_KEY_FRAME_X, DEFAULT_FRAME_RECT.x);
        int y = preferences.getInt(PREF_KEY_FRAME_Y, DEFAULT_FRAME_RECT.y);
        int width = preferences.getInt(PREF_KEY_FRAME_WIDTH, DEFAULT_FRAME_RECT.width);
        int height = preferences.getInt(PREF_KEY_FRAME_HEIGHT, DEFAULT_FRAME_RECT.height);
        return new Rectangle(x, y, width, height);
    }
    
    public void setFramePosition(Rectangle rectangle) {
        preferences.putInt(PREF_KEY_FRAME_X, rectangle.x);
        preferences.putInt(PREF_KEY_FRAME_Y, rectangle.y);
        preferences.putInt(PREF_KEY_FRAME_WIDTH, rectangle.width);
        preferences.putInt(PREF_KEY_FRAME_HEIGHT, rectangle.height);
    }
    
    public int getSelectedThemeIndex() {
        return preferences.getInt(PREF_KEY_THEME, DEFAULT_THEME_INDEX);
    }
    
    public int getSelectedBgColorIndex() {
        return preferences.getInt(PREF_KEY_BG_COLOR, DEFAULT_BG_COLOR_INDEX);
    }
    
    public int getSelectedMainTxtColorIndex() {
        return preferences.getInt(PREF_KEY_MAIN_TXT_COLOR, DEFAULT_MAIN_TXT_COLOR_INDEX);
    }
    
    public int getSelectedFooterTxtColorIndex() {
        return preferences.getInt(PREF_KEY_FOOTER_TXT_COLOR, DEFAULT_FOOTER_TXT_COLOR_INDEX);
    }
    
    public int getSelectedSideTxtColorIndex() {
        return preferences.getInt(PREF_KEY_SIDE_TXT_COLOR, DEFAULT_SIDE_TXT_COLOR_INDEX);
    }
    
    public int getSelectedTxtFontIndex() {
        return preferences.getInt(PREF_KEY_TXT_FONT, DEFAULT_TXT_FONT_INDEX);
    }
    
    public int getSelectedTxtSizeIndex() {
        return preferences.getInt(PREF_KEY_TXT_SIZE, DEFAULT_TXT_SIZE_INDEX);
    }
    
    public ClockTheme getSelectedTheme() {
        return THEME_OPTIONS[preferences.getInt(PREF_KEY_THEME, DEFAULT_THEME_INDEX)];
    }
    
    public ClockColor getSelectedBgColor() {
        return COLOR_OPTIONS[preferences.getInt(PREF_KEY_BG_COLOR, DEFAULT_BG_COLOR_INDEX)];
    }
    
    public ClockColor getSelectedMainTxtColor() {
        return COLOR_OPTIONS[preferences.getInt(PREF_KEY_MAIN_TXT_COLOR, DEFAULT_MAIN_TXT_COLOR_INDEX)];
    }
    
    public ClockColor getSelectedFooterTxtColor() {
        return COLOR_OPTIONS[preferences.getInt(PREF_KEY_FOOTER_TXT_COLOR, DEFAULT_FOOTER_TXT_COLOR_INDEX)];
    }
    
    public ClockColor getSelectedSideTxtColor() {
        return COLOR_OPTIONS[preferences.getInt(PREF_KEY_SIDE_TXT_COLOR, DEFAULT_SIDE_TXT_COLOR_INDEX)];
    }
    
    public Font getSelectedTxtFont() {
        return FONT_OPTIONS[preferences.getInt(PREF_KEY_TXT_FONT, DEFAULT_TXT_FONT_INDEX)];
    }
    
    public int getSelectedTxtSize() {
        return TXT_SIZE_OPTIONS[preferences.getInt(PREF_KEY_TXT_SIZE, DEFAULT_TXT_SIZE_INDEX)];
    }
    
    public int getClosestTxtSizeIndex(int size) {
        int result = 0;
        for (int i = 0; i < TXT_SIZE_OPTIONS.length; i++){
            if (size >= TXT_SIZE_OPTIONS[i]){
                result = i;
            }
        }
        return result;
    }
    
    public void setTheme(int index) {
        if (index > THEME_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_THEME, index);
    }
    
    public void setBgColor(int index) {
        if (index > COLOR_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_BG_COLOR, index);
    }
    
    public void setMainTxtColor(int index) {
        if (index > COLOR_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_MAIN_TXT_COLOR, index);
    }
    
    public void setFooterTxtColor(int index) {
        if (index > COLOR_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_FOOTER_TXT_COLOR, index);
    }
    
    public void setSideTxtColor(int index) {
        if (index > COLOR_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_SIDE_TXT_COLOR, index);
    }
    
    public void setTxtFont(int index) {
        if (index > FONT_OPTIONS.length - 1 || index < 0) {
            throw new IllegalArgumentException();
        }
        preferences.putInt(PREF_KEY_TXT_FONT, index);
    }
    
    public void setTxtSize(int index) {
        if (index > TXT_SIZE_OPTIONS.length - 1 || index < 0) {
            return;
        }
        preferences.putInt(PREF_KEY_TXT_SIZE, index);
    }
    

}
