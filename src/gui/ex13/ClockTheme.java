package gui.ex13;

import java.awt.Font;

public class ClockTheme {
    private String label;
    private ClockColor bgColor;
    private ClockColor mainTxtColor;
    private ClockColor footerTxtColor;
    private ClockColor sideTxtColor;
    private Font txtFont;
    
    public ClockTheme(String label, ClockColor bgColor, ClockColor mainTxtColor, ClockColor footerTxtColor, ClockColor sideTxtColor, Font txtFont) {
        this.label = label;
        this.bgColor = bgColor;
        this.mainTxtColor = mainTxtColor;
        this.footerTxtColor = footerTxtColor;
        this.sideTxtColor = sideTxtColor;
        this.txtFont = txtFont;
    }

    public String label() {
        return label;
    }

    public ClockColor bgColor() {
        return bgColor;
    }

    public ClockColor mainTxtColor() {
        return mainTxtColor;
    }

    public ClockColor footerTxtColor() {
        return footerTxtColor;
    }

    public ClockColor sideTxtColor() {
        return sideTxtColor;
    }

    public Font txtFont() {
        return txtFont;
    }
    
}
