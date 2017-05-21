package gui.ex22;

import java.awt.Color;

public class ClockTheme {
    private String label;
    private Color bgColor;
    private Color fgColor;
    private int fontIndex;
    
    public ClockTheme(String label, Color bgColor, Color fgColor, int fontIndex) {
        this.label = label;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.fontIndex = fontIndex;
    }

    @Override
    public String toString() {
        return label;
    }

    public String label() {
        return label;
    }

    public Color bgColor() {
        return bgColor;
    }

    public Color fgColor() {
        return fgColor;
    }

    public int fontIndex() {
        return fontIndex;
    }

}
