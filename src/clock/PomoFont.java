package clock;

import javafx.scene.text.Font;

enum PomoFont {
    ICON_50(50, Type.ICON), ICON_40(40, Type.ICON), ICON_30(30, Type.ICON),
    TEXT_50(50, Type.TEXT), TEXT_30(30, Type.TEXT), TEXT_20(20, Type.TEXT);
    
    private PomoFont(int size, Type type) {
        this.font = type.createIcon(size);
    }

    private Font font;

    Font get() {
        return font;
    }

    private enum Type {
        ICON{
            Font createIcon(int size){
                return Font.loadFont(this.getClass().getResource("font2/fontawesome-webfont.ttf").toExternalForm(), size);
            }
        },
        TEXT{
            Font createIcon(int size){
                return new Font(size);
            }
        };

        abstract Font createIcon(int size);
    }
}
