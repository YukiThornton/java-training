package gui.ex14;

import java.awt.Color;

@SuppressWarnings("serial")
public class ClockColor extends Color{
    
    private String label;

    public ClockColor(String label, int r, int g, int b) {
        super(r, g, b);
        this.label = label;
    }
    
    public String label() {
        return label;
    }
}
