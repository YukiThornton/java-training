package clock;

import java.util.Arrays;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

final class ViewHelper {
    public static BorderPane createEmptyPane() {
        return new BorderPane();
    }

    public static Scene createScene(BorderPane pane) {
        return new Scene(pane);
    }

    public static HBox createHBox(Pos posValue, Node... nodes) {
        Node[] nodesWithoutNull = removeNull(nodes);
        HBox box = new HBox(nodesWithoutNull.length);
        if (posValue != null) {
            box.setAlignment(posValue);
        }
        box.getChildren().addAll(nodesWithoutNull);
        return box;
    }

    public static VBox createVBox(Pos posValue, Node... nodes) {
        Node[] nodesWithoutNull = removeNull(nodes);
        VBox box = new VBox(nodesWithoutNull.length);
        if (posValue != null) {
            box.setAlignment(posValue);
        }
        box.getChildren().addAll(nodesWithoutNull);
        return box;
    }

    public static Label createTextLabel(String text, Font font, Color textFill) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(textFill);
        return label;
    }

    public static void showAlertAndWait(String title, String content, AlertType type, boolean onTop){
        Alert alert = new Alert(type);
        if (onTop) {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static Node[] removeNull(Node[] nodes) {
        return Arrays.stream(nodes).filter(e -> e != null).toArray(Node[]::new);
    }
}
