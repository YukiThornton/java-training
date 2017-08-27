package clock;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NodeTools {
    public static final Font FONT_MEDIUM = new Font(50);
    public static final Font FONT_SMALL = new Font(30);
    public static final Font FONT_TINY = new Font(20);

    public static void hideNode(Node node, boolean visibleAndManaged) {
        node.setVisible(visibleAndManaged);
        node.setManaged(visibleAndManaged);
    }

    public static void ensureVisibleInScrollPane(ScrollPane pane, Node node) {
        double width = pane.getContent().getBoundsInLocal().getWidth();
        double height = pane.getContent().getBoundsInLocal().getHeight();

        double x = node.getBoundsInParent().getMaxX();
        double y = node.getBoundsInParent().getMaxY();

        pane.setVvalue(y/height);
        pane.setHvalue(x/width);
        node.requestFocus();
    }

    public static Label createIconBtn(String text, Font font, Color color) {
        Label label = createTextBtn(text, font, color);
        label.setStyle("-fx-font-family: \'Material Icons\'; -fx-font-size: 60; -fx-fill: firebrick;");
        return label;
    }

    public static Label createTextBtn(String text, Font font, Color color) {
        Label label = new Label(text);
        label.setFont(font);
        label.setTextFill(color);
        return label;
    }

    public static TextField createTextField(String initialText, Font font, double maxWidth, double maxHeight, boolean visible) {
        TextField textField = new TextField(initialText);
        textField.setFont(font);
        textField.setMaxSize(maxWidth, maxHeight);
        textField.setVisible(visible);
        return textField;
    }

    public static void showHiddenTextField(TextField textField, String text) {
        textField.setText(text);
        textField.setVisible(true);
        textField.requestFocus();
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

    public static ScrollPane wrapWithScrollPane(Node view) {
        ScrollPane pane = new ScrollPane(view);
        pane.setHbarPolicy(ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollBarPolicy.NEVER);
        pane.setFitToHeight(true);
        pane.setFitToWidth(true);
        return pane;
    }

    public static void acceptOnEnterAndSetInvisibleOnEscape(TextField textField, Consumer<String> onEnter) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                case ENTER:
                    onEnter.accept(textField.getText());
                    break;
                case ESCAPE:
                    textField.setVisible(false);
                    break;
                default:
                    break;
                }
            }
        });
    }

    public static void setInvisibleOnFocusLost(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                    Boolean newValue) {
                    if (!newValue) {
                        node.setVisible(false);
                    }
            }
        });
    }

}
