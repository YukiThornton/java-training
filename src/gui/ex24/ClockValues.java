package gui.ex24;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.prefs.Preferences;

public class ClockValues {
    public static final Font SYSTEM_FONT = new Font("Dialog", Font.PLAIN, 15);
    public static final Dimension MIN_FRAME_SIZE = new Dimension(150, 150);
    public static final Dimension DEFAULT_FRAME_SIZE = new Dimension(600, 400);
    public static final Point DEFAULT_POSITION = new Point(0, 0);
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
    public static final int DEFAULT_FONT_INDEX = 0;

    public static final Color DEFAULT_BG_COLOR = new Color(252, 243, 242);
    public static final Color DEFAULT_FG_COLOR = new Color(193, 31, 9);
    
    public static final DecorativeFrame DEFAULT_DECORATION = DecorativeFrame.OVAL;

    public static final ClockTheme THEME_DEFAULT = new ClockTheme("Default", DEFAULT_BG_COLOR, DEFAULT_FG_COLOR, DEFAULT_FONT_INDEX, DEFAULT_DECORATION);
    public static final ClockTheme THEME_SIMPLE = new ClockTheme("Simple", Color.WHITE, Color.BLACK, 7, DecorativeFrame.NONE);
    public static final ClockTheme THEME_DARK = new ClockTheme("Dark", new Color(105, 105, 105), new Color(211, 211, 211), 2, DecorativeFrame.OVAL);
    public static final ClockTheme THEME_CHOCO = new ClockTheme("Mint Chocolate", new Color(127, 255, 212), new Color(77, 49, 49), 6, DecorativeFrame.OVAL);
    public static final ClockTheme[] THEME_OPTIONS = {THEME_DEFAULT, THEME_SIMPLE, THEME_DARK, THEME_CHOCO};
    public static final int DEFAULT_THEME_INDEX = 0;

    public static final String POPUP_COMMAND_VIEW = "View";
    public static final String POPUP_COMMAND_PREF = "Preferences";
    public static final String POPUP_COMMAND_EXIT = "Exit";
    public static final String POPUP_COMMAND_MODE = "Mode";
    public static final String POPUP_COMMAND_MODE_STANDARD = "Standard Clock";
    public static final String POPUP_COMMAND_MODE_TASK = "+Stopwatch";
    public static final String POPUP_COMMAND_TASK = "Stopwatch";
    public static final String POPUP_COMMAND_TASK_START = "Start";
    public static final String POPUP_COMMAND_TASK_PAUSE = "Pause";
    public static final String POPUP_COMMAND_TASK_RESET = "Reset";
    public static final String POPUP_COMMAND_TASK_RENAME = "Rename";
    public static final String MODE_MESSAGE_TASK = "-- : --";
    public static final String DIALOG_MESSAGE_RENAME_TASK = "Type a new name.\nYou can use only alphanumerics.";
    public static final String DIALOG_MESSAGE_RENAME_TASK_AFTER_VALID_ERR = "Not that name.\nYou can use only alphanumerics.\nType a different one.";

    private final static String PREF_KEY_FRAME_DIMENSION = "okuno_clock_ex24_1.0_frame_dimension";
    private final static String PREF_KEY_FRAME_POINT = "okuno_clock_ex24_1.0_frame_point";
    private final static String PREF_KEY_THEME_INDEX = "okuno_clock_ex24_1.0_theme_index";
    private final static String PREF_KEY_FONT_INDEX = "okuno_clock_ex24_1.0_font_index";
    private final static String PREF_KEY_FONT_SIZE_INDEX = "okuno_clock_ex24_1.0_font_size_index";
    private final static String PREF_KEY_BG_COLOR = "okuno_clock_ex24_1.0_bg_color";
    private final static String PREF_KEY_FG_COLOR = "okuno_clock_ex24_1.0_fg_color";
    private final static String PREF_KEY_DECORATION = "okuno_clock_ex24_1.0_decoration";
    private final static String PREF_KEY_WINDOW_FONT_SIZE_RULE = "okuno_clock_ex24_1.0_window_font_size_rule";
    private final static String PREF_KEY_TASK_STRING = "okuno_clock_ex24_1.0_task_string";
    private final static String PREF_DEFAULT_VAL_TASK_STRING = "Task1:0;Task2:0;Task3:0;Task4:0;Task5:0";
    public final static int TASK_AMOUNT = 5;
    private Preferences preferences;

    private int themeIndex;
    private int fontIndex;
    private int fontSizeIndex;
    private Color bgColorField;
    private Color fgColorField;
    private DecorativeFrame decoration;
    private boolean windowSizeDeterminesFontSize;
    
    enum FontSizeRatioOptions {
        STANDARD(1), SMALLER(0.6), TINY(0.4);
        
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
        preferences = Preferences.userNodeForPackage(this.getClass());
        themeIndex = preferences.getInt(PREF_KEY_THEME_INDEX, DEFAULT_THEME_INDEX);
        fontIndex = preferences.getInt(PREF_KEY_FONT_INDEX, DEFAULT_FONT_INDEX);
        windowSizeDeterminesFontSize = preferences.getBoolean(PREF_KEY_WINDOW_FONT_SIZE_RULE, DEFAULT_WINDOW_SIZE_DETEMINES_FONT_SIZE);
        setFontSize(preferences.getInt(PREF_KEY_FONT_SIZE_INDEX, DEFAULT_FONT_SIZE_INDEX));
        bgColorField = (Color)savedObject(PREF_KEY_BG_COLOR, DEFAULT_BG_COLOR);
        fgColorField = (Color)savedObject(PREF_KEY_FG_COLOR, DEFAULT_FG_COLOR);
        decoration = (DecorativeFrame)savedObject(PREF_KEY_DECORATION, DEFAULT_DECORATION);
    }

    private ClockValues(ClockValues oldValues) {
        preferences = oldValues.preferences();
        themeIndex = oldValues.themeIndex();
        fontIndex = oldValues.fontIndex();
        windowSizeDeterminesFontSize = oldValues.windowSizeDeterminesFontSize();
        setFontSize(oldValues.fontSizeIndex());
        bgColorField = oldValues.bgColor();
        fgColorField = oldValues.fgColor();
        decoration = oldValues.decoration();
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

    public Rectangle initialRectangle() {
        Dimension dimension = (Dimension)savedObject(PREF_KEY_FRAME_DIMENSION, DEFAULT_FRAME_SIZE);
        Point point = (Point)savedObject(PREF_KEY_FRAME_POINT, DEFAULT_POSITION);
        return new Rectangle(point, dimension);
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

    public Preferences preferences() {
        return preferences;
    }

    public String taskString() {
        return preferences.get(PREF_KEY_TASK_STRING, PREF_DEFAULT_VAL_TASK_STRING);
    }

    public void saveTaskString(String taskString) {
        preferences.put(PREF_KEY_TASK_STRING, taskString);
    }

    public void saveFrameBounds(Rectangle bounds) {
        saveObject(bounds.getSize(), PREF_KEY_FRAME_DIMENSION);
        saveObject(bounds.getLocation(), PREF_KEY_FRAME_POINT);
    }

    public void saveViewPref() {
        preferences.putInt(PREF_KEY_THEME_INDEX, themeIndex);
        preferences.putInt(PREF_KEY_FONT_INDEX, fontIndex);
        preferences.putInt(PREF_KEY_FONT_SIZE_INDEX, fontSizeIndex);
        preferences.putBoolean(PREF_KEY_WINDOW_FONT_SIZE_RULE, windowSizeDeterminesFontSize);
        saveObject(bgColorField, PREF_KEY_BG_COLOR);
        saveObject(fgColorField, PREF_KEY_FG_COLOR);
        saveObject(decoration, PREF_KEY_DECORATION);
    }

    private void saveObject(Object object, String prefKey) {
        preferences.putByteArray(prefKey, objectToByteArray(object));
    }

    private Object savedObject(String prefKey, Object defualt) {
        byte[] defaultObjBytes = objectToByteArray(defualt);
        byte[] resultBytes = preferences.getByteArray(prefKey, defaultObjBytes);
        return byteArrayToObject(resultBytes);
    }

    private byte[] objectToByteArray(Object object) {
        ByteArrayOutputStream byteStream = null;
        ObjectOutputStream objStream = null;
        byte[] result = null;
        try {
            byteStream = new ByteArrayOutputStream();
            objStream = new ObjectOutputStream(byteStream);
            objStream.writeObject(object);
            result = byteStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

    private Object byteArrayToObject(byte[] bytes) {
        ByteArrayInputStream byteStream = null;
        ObjectInputStream objStream = null;
        Object result = null;
        try {
            byteStream = new ByteArrayInputStream(bytes);
            objStream = new ObjectInputStream(byteStream);
            result = objStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objStream != null) {
                    objStream.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

}