package clock;

import javafx.scene.paint.Color;

public enum ColorSet {
    GRAY("#ffffff", "#b3b3b3", "#f6f6f6", "#666666"),
    BLUE("#42a4f4", "#0d7dd9", "#6fa0c8", "#3f78a6"),
    YELLOW("#e1de00", "#999600", "#b9b727", "#7e7d1b");

    private String saturatedLight;
    private String saturatedDark;
    private String light;
    private String dark;
    private Color saturatedLightColor;
    private Color saturatedDarkColor;
    private Color lightColor;
    private Color darkColor;

    private ColorSet(String saturatedLight, String saturatedDark, String light, String dark) {
        this.saturatedLight = saturatedLight;
        this.saturatedDark = saturatedDark;
        this.light = light;
        this.dark = dark;
        this.saturatedLightColor = Color.web(saturatedLight);
        this.saturatedDarkColor = Color.web(saturatedDark);
        this.lightColor = Color.web(light);
        this.darkColor = Color.web(dark);
    }

    public String toRGBTxt(Color color) {
        if (color.equals(saturatedLightColor)) {
            return saturatedLight;
        }
        if (color.equals(saturatedDarkColor)) {
            return saturatedDark;
        }
        if (color.equals(lightColor)) {
            return light;
        }
        if (color.equals(darkColor)) {
            return dark;
        }
        throw new IllegalArgumentException("Only colors of ColorSet object are acceptable.");
    }

    public String saturatedLight() {
        return saturatedLight;
    }

    public String saturatedDark() {
        return saturatedDark;
    }

    public String light() {
        return light;
    }

    public String dark() {
        return dark;
    }

    public Color saturatedLightColor() {
        return saturatedLightColor;
    }

    public Color saturatedDarkColor() {
        return saturatedDarkColor;
    }

    public Color lightColor() {
        return lightColor;
    }

    public Color darkColor() {
        return darkColor;
    }
}
