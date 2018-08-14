package clock;

import static clock.ViewHelper.*;

import java.awt.Dimension;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class EditableTextImpl implements EditableText {

    static class Builder implements EditableText.Builder {

        private String text;
        private AppFont font;
        private ColorPalette colorPalette;
        private BooleanSupplier editable;
        private Predicate<String> textValidation;
        private Consumer<String> onInvalidInput;
        private Consumer<String> onValidInput;

        Builder(String text, AppFont appFont, ColorPalette colorPalette) {
            this.text = text;
            this.font = appFont;
            this.colorPalette = colorPalette;
        }

        @Override
        public EditableText.Builder defineEditableCondition(BooleanSupplier editable){
            this.editable = editable;
            return this;
        }

        @Override
        public EditableText.Builder setTextValidation(Predicate<String> validation) {
            this.textValidation = validation;
            return this;
        }

        @Override
        public EditableText.Builder onInvalidInput(Consumer<String> action) {
            this.onInvalidInput = action;
            return this;
        }

        @Override
        public EditableText.Builder onValidInput(Consumer<String> action) {
            this.onValidInput = action;
            return this;
        }

        @Override
        public EditableText build() {
            if (editable == null || textValidation == null || onInvalidInput == null || onValidInput == null) {
                throw new IllegalStateException("Not enough parameters");
            }
            return new EditableTextImpl(text, font, colorPalette, editable, textValidation, onInvalidInput, onValidInput);
        }
    }

    private static Dimension MAX_TEXT_FIELD_SIZE = new Dimension(150, 10);

    private BooleanSupplier editable;
    private Predicate<String> textValidation;
    private Consumer<String> onInvalidInput;
    private Consumer<String> onValidInput;
    private final Label label;
    private final TextField textField;
    private final StackPane labelAndTextField;

    private EditableTextImpl(String text, AppFont appFont, ColorPalette colorPalette, BooleanSupplier editable,
            Predicate<String> validation, Consumer<String> onInvalidInput, Consumer<String> onValidInput) {
        this.editable = editable;
        this.textValidation = validation;
        this.onInvalidInput = onInvalidInput;
        this.onValidInput = onValidInput;

        label = initReadOnlyLabel(text, appFont.get(), colorPalette);
        textField = initTextField(text, appFont.get());
        labelAndTextField = new StackPane(label, textField);
    }

    private Label initReadOnlyLabel(String text, Font font, ColorPalette colorPalette) {
        Label label = createTextLabel(text, font);
        setColorOnLabel(label, colorPalette);
        label.setOnMouseClicked((event) -> {
            if (editable.getAsBoolean()) {
                showEditMode();
            }
        });
        return label;
    }

    private void setColorOnLabel(Label label, ColorPalette colorPalette) {
        Color defaultColor = colorPalette.get(ColorPalette.Key.LIGHT);
        label.setTextFill(defaultColor);
        label.setOnMouseEntered((event) -> {
            if (editable.getAsBoolean()) {
                label.setTextFill(colorPalette.get(ColorPalette.Key.DARK));
            }
        });
        label.setOnMouseExited((event) -> {
            if (editable.getAsBoolean()) {
                label.setTextFill(defaultColor);
            }
        });
    }

    private TextField initTextField(String initialText, Font font) {
        TextField textField = createTextField(initialText, font, MAX_TEXT_FIELD_SIZE);
        setInvisibleAfterFocusLeft(textField);
        textField.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case ENTER:
                    invokeTextValidation(textField.getText());
                    break;
                case ESCAPE:
                    textField.setVisible(false);
                    break;
                default:
                    break;
            }
        });
        textField.setVisible(false);
        return textField;
    }

    private void invokeTextValidation(String text) {
        if (textValidation.test(text)) {
            changeText(text);
            textField.setVisible(false);
            onValidInput.accept(text);
        } else {
            onInvalidInput.accept(text);
        }
    }

    @Override
    public Region get() {
        return labelAndTextField;
    }

    @Override
    public void showReadOnlyMode() {
        textField.setVisible(false);
    }

    @Override
    public void showEditMode() {
        textField.setText(label.getText());
        textField.setVisible(true);
        textField.requestFocus();
    }

    @Override
    public void changeText(String newText) {
        // TODO: validation
        label.setText(newText);
    }

    @Override
    public void changeTextColorPalette(ColorPalette newColorPalette) {
        // TODO: validation
        setColorOnLabel(label, newColorPalette);
    }

}
