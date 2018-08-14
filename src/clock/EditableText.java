package clock;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.scene.layout.Region;

public interface EditableText {

    Region get();
    void showReadOnlyMode();
    void showEditMode();
    void changeText(String newText);
    void changeTextColorPalette(ColorPalette colorPalette);

    interface Builder {
        Builder defineEditableCondition(BooleanSupplier editable);
        Builder setTextValidation(Predicate<String> validation);
        Builder onInvalidInput(Consumer<String> action);
        Builder onValidInput(Consumer<String> action);
        EditableText build();
    }
}
