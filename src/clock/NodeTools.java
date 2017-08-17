package clock;

import java.util.function.Consumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class NodeTools {
    public static final Font FONT_MEDIUM = new Font(50);
    public static final Font FONT_SMALL = new Font(30);
    public static final Font FONT_TINY = new Font(20);

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
