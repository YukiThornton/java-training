package gui.ex22;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

public class ClockValues {
    public static final Font SYSTEM_FONT = new Font("Dialog", Font.PLAIN, 15);
    public static final Dimension MIN_FRAME_SIZE = new Dimension(150, 150);
    public static final Dimension DEFAULT_FRAME_SIZE = new Dimension(600, 400);
    public static final Dimension FIXED_DIALOG_SIZE = new Dimension(400, 550);
    
    public static final Integer[] FONT_SIZE_OPTIONS = {16, 20, 24, 28, 36, 50, 60, 80, 100, 120, 150, 180, 200};
    public static final int DEFAULT_FONT_SIZE_INDEX = 5;
    public static final boolean DEFAULT_WINDOW_SIZE_DETEMINES_FONT_SIZE = true;

    private static final Font FONT_DEFAULT = new ClockFont(Font.MONOSPACED, Font.BOLD, 24);
    private static final Font FONT_ARIAL_BLACK = new ClockFont("Arial Black", Font.PLAIN, 24);
    private static final Font FONT_IMPACT = new ClockFont("Impact", Font.PLAIN, 24);
    private static final Font FONT_GEORGIA = new ClockFont("Georgia", Font.PLAIN, 24);
    private static final Font FONT_COUERIER = new ClockFont("Courier New", Font.BOLD, 24);
    private static final Font FONT_NEW_ROMAN = new ClockFont("Times New Roman", Font.PLAIN, 24);
    private static final Font FONT_TREBUCHET = new ClockFont("Trebuchet MS", Font.PLAIN, 24);
    private static final Font FONT_VERDANA = new ClockFont("Verdana", Font.PLAIN, 24);
    private static final Font FONT_COMIC_SANS = new ClockFont("Comic Sans MS", Font.PLAIN, 24);
    public static final Font[] FONT_OPTIONS = {FONT_DEFAULT, FONT_ARIAL_BLACK, FONT_IMPACT, FONT_GEORGIA, FONT_COUERIER, 
                                FONT_NEW_ROMAN, FONT_TREBUCHET, FONT_VERDANA, FONT_COMIC_SANS};
    private static final int DEAFULT_FONT_INDEX = 0;

    public static final Color DEFAULT_BG_COLOR = new Color(252, 243, 242);
    public static final Color DEFAULT_FG_COLOR = new Color(193, 31, 9);

    public static final ClockTheme THEME_DEFAULT = new ClockTheme("Default", DEFAULT_BG_COLOR, DEFAULT_FG_COLOR, DEAFULT_FONT_INDEX, DecorativeFrame.OVAL);
    public static final ClockTheme THEME_SIMPLE = new ClockTheme("Simple", Color.WHITE, Color.BLACK, 7, DecorativeFrame.NONE);
    public static final ClockTheme THEME_DARK = new ClockTheme("Dark", new Color(105, 105, 105), new Color(211, 211, 211), 2, DecorativeFrame.OVAL);
    public static final ClockTheme THEME_CHOCO = new ClockTheme("Mint Chocolate", new Color(127, 255, 212), new Color(77, 49, 49), 6, DecorativeFrame.OVAL);
    public static final ClockTheme[] THEME_OPTIONS = {THEME_DEFAULT, THEME_SIMPLE, THEME_DARK, THEME_CHOCO};
    public static final int DEFAULT_THEME_INDEX = 0;

    private int themeIndex;
    private int fontIndex;
    private int fontSizeIndex;
    private final int initialFontSize;
    private Color bgColorField;
    private Color fgColorField;
    private DecorativeFrame decoration;
    private boolean windowSizeDeterminesFontSize;
    
    enum FontSizeRatioOptions {
        STANDARD(1), SMALLER(0.6);
        
        private double ratio;
        
        private FontSizeRatioOptions(double ratio) {
            this.ratio = ratio;
        }
    }

    enum DecorativeFrame {
        OVAL(0, "Circle"), NONE(1, "None");
        
        int index;
        String name;
        
        private DecorativeFrame(int index, String name) {
            this.index = index;
            this.name = name;
        }
    }

    public ClockValues() {
        themeIndex = DEFAULT_THEME_INDEX;
        ClockTheme theme = THEME_OPTIONS[DEFAULT_THEME_INDEX];
        fontIndex = theme.fontIndex();
        setFontSize(DEFAULT_FONT_SIZE_INDEX);
        initialFontSize = fontSize(FontSizeRatioOptions.STANDARD, DEFAULT_FRAME_SIZE.width);
        bgColorField = theme.bgColor();
        fgColorField = theme.fgColor();
        decoration = theme.decoration();
        windowSizeDeterminesFontSize = DEFAULT_WINDOW_SIZE_DETEMINES_FONT_SIZE;
    }

    private ClockValues(ClockValues oldValues) {
        themeIndex = oldValues.themeIndex();
        fontIndex = oldValues.fontIndex();
        setFontSize(oldValues.fontSizeIndex());
        initialFontSize = oldValues.initialFontSize;
        bgColorField = oldValues.bgColor();
        fgColorField = oldValues.fgColor();
        decoration = oldValues.decoration();
        windowSizeDeterminesFontSize = oldValues.windowSizeDeterminesFontSize();
    }

    public ClockValues snapShot() {
        return new ClockValues(this);
    }

    public void set(int themeIndex) {
        if (themeIndex < 0) {
            throw new IllegalArgumentException("Something went wrong.");
        }
        setClockTheme(themeIndex);
        ClockTheme theme = THEME_OPTIONS[themeIndex];
        setFont(theme.fontIndex());
        setBgColor(theme.bgColor());
        setFgColor(theme.fgColor());
        setDecoration(theme.decoration());
    }

    public Color bgColor() {
        return bgColorField;
    }

    public void setBgColor(Color bgColor) {
        this.bgColorField = bgColor;
    }

    public Color fgColor() {
        return fgColorField;
    }

    public void setFgColor(Color fgColor) {
        this.fgColorField = fgColor;
    }

    public ClockTheme clockTheme() {
        return THEME_OPTIONS[themeIndex];
    }

    public int themeIndex() {
        return themeIndex;
    }

    public void setClockTheme(int index) {
        themeIndex = index;
    }

    public Font font(int panelWidth) {
        return font(FontSizeRatioOptions.STANDARD, panelWidth);
    }

    public Font font(FontSizeRatioOptions ratio, int panelWidth) {
        Font font = FONT_OPTIONS[fontIndex];
        return new ClockFont(font.getName(), font.getStyle(), fontSize(ratio, panelWidth));
    }

    public int fontIndex() {
        return fontIndex;
    }

    public int initialFontSize() {
        return initialFontSize;
    }

    public void setFont(int index) {
        fontIndex = index;
    }

    public int fontSize() {
        if (windowSizeDeterminesFontSize) {
            throw new IllegalStateException("Something went wrong!");
        }
        return calcFontSize(FontSizeRatioOptions.STANDARD, -1);
    }

    public int fontSize(int panelWidth) {
        if (!windowSizeDeterminesFontSize) {
            throw new IllegalStateException("Something went wrong!");
        }
        return calcFontSize(FontSizeRatioOptions.STANDARD, panelWidth);
    }

    public int fontSize(FontSizeRatioOptions ratio, int panelWidth) {
        return calcFontSize(ratio, panelWidth);
    }

    public int fontSizeIndex() {
        return fontSizeIndex;
    }

    public void setFontSize(int index) {
        if (windowSizeDeterminesFontSize) {
            fontSizeIndex = -1;
        } else {
            fontSizeIndex = index;
        }
    }

    public DecorativeFrame decoration() {
        return decoration;
    }

    public void setDecoration(DecorativeFrame decoration) {
        this.decoration = decoration;
    }

    public boolean windowSizeDeterminesFontSize() {
        return windowSizeDeterminesFontSize;
    }

    public void setWindowSizeDeterminesFontSize(boolean windowSizeDeterminesFontSize) {
        this.windowSizeDeterminesFontSize = windowSizeDeterminesFontSize;
        setFontSize(DEFAULT_FONT_SIZE_INDEX);
    }

    private int calcFontSize(FontSizeRatioOptions ratio, int panelWidth) {
        if (windowSizeDeterminesFontSize) {
            if (decoration == DecorativeFrame.NONE) {
                return (int)(FONT_SIZE_OPTIONS[DEFAULT_FONT_SIZE_INDEX] * panelWidth / DEFAULT_FRAME_SIZE.width * ratio.ratio * 2);
            } else {
                return (int)(FONT_SIZE_OPTIONS[DEFAULT_FONT_SIZE_INDEX] * panelWidth / DEFAULT_FRAME_SIZE.width * ratio.ratio);
            }
        } else {
            return (int)(FONT_SIZE_OPTIONS[fontSizeIndex] * ratio.ratio);
        }
    }
    public int clockOvalWidth(int panelWidth) {
        if (windowSizeDeterminesFontSize) {
            return FONT_SIZE_OPTIONS[DEFAULT_FONT_SIZE_INDEX] * 9 / 10 * panelWidth / DEFAULT_FRAME_SIZE.width;
        } else {
            return FONT_SIZE_OPTIONS[fontSizeIndex] * 9 / 10;
        }
    }


}