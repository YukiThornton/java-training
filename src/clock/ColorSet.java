package clock;

import javafx.scene.paint.Color;

public enum ColorSet {
    BLUE("#80bfff", "#1745cf", "#9fbfdf", "#506295"),
    YELLOW("#f7e409", "#fdab02", "#d2c52d", "#bf9540");

    String remaining;
    String passed;
    String remainingDim;
    String passedDim;
    Color remainingColor;
    Color passedColor;
    Color remainingDimColor;
    Color passedDimColor;

    private ColorSet(String remaining, String passed, String remainingDim, String passedDim) {
        this.remaining = remaining;
        this.passed = passed;
        this.remainingDim = remainingDim;
        this.passedDim = passedDim;
        this.remainingColor = Color.web(remaining);
        this.passedColor = Color.web(passed);
        this.remainingDimColor = Color.web(remainingDim);
        this.passedDimColor = Color.web(passedDim);
    }

    public String remaining() {
        return remaining;
    }

    public String passed() {
        return passed;
    }

    public String remainingDim() {
        return remainingDim;
    }

    public String passedDim() {
        return passedDim;
    }

    public Color remainingColor() {
        return remainingColor;
    }

    public Color passedColor() {
        return passedColor;
    }

    public Color remainingDimColor() {
        return remainingDimColor;
    }

    public Color passedDimColor() {
        return passedDimColor;
    }
}
