package clock;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.scene.layout.Region;

public interface TimerCard {
    Region get();
    void showDeleteTimerButton();
    void hideDeleteTimerButton();

    interface Builder {
        Builder setValidatorForTimerName(Predicate<String> validator);
        Builder setValidatorForCountdownTime(Predicate<String> validator);
        Builder onInvalidInputForTimerName(Consumer<String> action);
        Builder onInvalidInputForCountdownTime(Consumer<String> action);
        Builder onTimerDeletionRequested(BiConsumer<TimerCard, TimerState> action);
        TimerCard build();
    }
}
