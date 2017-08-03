package gui.ex24;

import java.awt.Color;

import gui.ex24.ClockValues.DecorativeFrame;

public class ClockTheme {
    private String label;
    private Color bgColor;
    private Color fgColor;
    private int fontIndex;
    private DecorativeFrame decoration;
    
    public ClockTheme(String label, Color bgColor, Color fgColor, int fontIndex, DecorativeFrame decoration) {
        this.label = label;
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.fontIndex = fontIndex;
        this.decoration = decoration;
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

    public DecorativeFrame decoration() {
        return decoration;
    }

}
