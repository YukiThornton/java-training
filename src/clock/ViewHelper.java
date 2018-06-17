package clock;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

final class ViewHelper {
    public static HBox createHBox(Pos posValue, Node... nodes) {
        HBox box = new HBox(nodes.length);
        if (posValue != null) {
            box.setAlignment(posValue);
        }
        box.getChildren().addAll(nodes);
        return box;
    }

    public static VBox createVBox(Pos posValue, Node... nodes) {
        VBox box = new VBox(nodes.length);
        if (posValue != null) {
            box.setAlignment(posValue);
        }
        box.getChildren().addAll(nodes);
        return box;
    }
}
