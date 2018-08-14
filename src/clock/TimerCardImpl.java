package clock;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

class TimerCardImpl implements TimerCard {

    static class Builder implements TimerCard.Builder {

        private TimerState state;
        private Predicate<String> validatorForTimerName;
        private Predicate<String> validatorForCountdownTime;
        private Consumer<String> onInvalidInputForTimerName;
        private Consumer<String> onInvalidInputForCountdownTime;
        private BiConsumer<TimerCard, TimerState> onTimerDeletionRequested;

        Builder(TimerState state) {
            this.state = state;
        }

        @Override
        public TimerCard.Builder setValidatorForTimerName(Predicate<String> validator) {
            validatorForTimerName = validator;
            return this;
        }

        @Override
        public TimerCard.Builder setValidatorForCountdownTime(Predicate<String> validator) {
            validatorForCountdownTime = validator;
            return this;
        }

        @Override
        public TimerCard.Builder onInvalidInputForTimerName(Consumer<String> action) {
            this.onInvalidInputForTimerName = action;
            return this;
        }

        @Override
        public TimerCard.Builder onInvalidInputForCountdownTime(Consumer<String> action) {
            this.onInvalidInputForCountdownTime = action;
            return this;
        }

        @Override
        public TimerCard.Builder onTimerDeletionRequested(BiConsumer<TimerCard, TimerState> action) {
            this.onTimerDeletionRequested = action;
            return this;
        }

        @Override
        public TimerCard build() {
            if ((state == null)
                    || (onInvalidInputForTimerName == null)
                    || (onInvalidInputForCountdownTime == null)
                    || (onTimerDeletionRequested == null)
               ) {
                throw new IllegalStateException("Not enough parameter");
            }
            return new TimerCardImpl(this);
        }
    }

    private TimerState state;
    private final EditableText editableTextForTimerName;
    private final EditableText editableTextForCountdownTime;
    private final ControlButton deleteTimerButton;
    private final VBox root;

    private TimerCardImpl(Builder builder) {
        this.state = builder.state;
        editableTextForTimerName = new EditableTextImpl.Builder("delete later", AppFont.TEXT_30, state.colorPalette())
                .defineEditableCondition(() -> !state.isRunning())
                .setTextValidation(text -> builder.validatorForTimerName.test(text))
                .onInvalidInput(builder.onInvalidInputForTimerName)
                .onValidInput(text -> state.setTimerName(text))
                .build();
        editableTextForCountdownTime = new EditableTextImpl.Builder("15", AppFont.TEXT_50, state.colorPalette())
                .defineEditableCondition(() -> !state.isRunning())
                .setTextValidation(text -> builder.validatorForCountdownTime.test(text))
                .onInvalidInput(builder.onInvalidInputForCountdownTime)
                .onValidInput(text -> System.out.println("set new countdown time"))
                .build();
        deleteTimerButton = IconButton.create(IconButton.Type.DELETE_TIMER);
        deleteTimerButton.setOnMouseClicked(e -> {
            if (!this.state.isRunning()) {
                builder.onTimerDeletionRequested.accept(this, this.state);
            }
        });
        deleteTimerButton.hide();
        BorderPane topBox = new BorderPane();
//        topBox.setLeft(hiddenDeleteBtn);
        topBox.setCenter(editableTextForTimerName.get());
        topBox.setRight(deleteTimerButton.get());
        root = new VBox(topBox, editableTextForCountdownTime.get());
        root.setAlignment(Pos.TOP_CENTER);
    }

    @Override
    public Region get() {
        return root;
    }

    @Override
    public void showDeleteTimerButton() {
        deleteTimerButton.show();
    }

    @Override
    public void hideDeleteTimerButton() {
        deleteTimerButton.hide();
    }

}
