package clock;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;

interface ControlButton {
    Control get();
    void setOnMouseClicked(EventHandler<MouseEvent> handler);
    void hide();
    void show();
    boolean canChangeColorPalette();
    void changeColorPalette(ColorPalette to);
}
