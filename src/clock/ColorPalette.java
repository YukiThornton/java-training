package clock;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;

enum ColorPalette {

    GRAY("#ffffff", "#b3b3b3", "#f6f6f6", "#666666"),
    BLUE("#42a4f4", "#0d7dd9", "#6fa0c8", "#3f78a6"),
    YELLOW("#e1de00", "#999600", "#b9b727", "#7e7d1b");

    static enum Key {
        SATURATED_LIGHT,
        SATURATED_DARK,
        LIGHT,
        DARK;
    }

    public static final ColorPalette BG_COLORS = ColorPalette.GRAY;
    public static final ColorPalette.Key BG_COLOR_KEY = ColorPalette.Key.LIGHT;

    private Map<Key, Value> map;

    private ColorPalette(String saturatedLight, String saturatedDark, String light, String dark) {
        Map<Key, Value> modifiableMap = new HashMap<>();
        modifiableMap.put(Key.SATURATED_LIGHT, new Value(saturatedLight));
        modifiableMap.put(Key.SATURATED_DARK, new Value(saturatedDark));
        modifiableMap.put(Key.LIGHT, new Value(light));
        modifiableMap.put(Key.DARK, new Value(dark));

        map = Collections.unmodifiableMap(modifiableMap);
    }

    private class Value {
        private String textFormat;
        private Color colorFormat;

        Value(String text) {
            textFormat = text;
            colorFormat = Color.web(text);
        }
    }

    Color get(Key key) {
        return map.get(key).colorFormat;
    }

    String getTextOf(Key key) {
        return map.get(key).textFormat;
    }
}
