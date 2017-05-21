package gui.ex22;

import java.awt.Font;

@SuppressWarnings("serial")
public class ClockFont extends Font{

    public ClockFont(String name, int style, int size) {
        super(name, style, size);
    }

    @Override
    public String toString() {
        return getName();
    }
}
