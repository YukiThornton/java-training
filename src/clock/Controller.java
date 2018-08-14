package clock;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.stage.Stage;

class Controller {

    private static final TimerType[] INITIAL_TIMER_TYPES = {TimerType.WORK_DEFAULT, TimerType.BREAK_DEFAULT};
    private static final int INITIAL_TIMER_INDEX = 0;
    private static final long PERIODIC_VIEW_UPDATE_INTERVAL = 500;
    private static final int MAX_COUNTDOWN_TIME = 999;
    private static final int MAX_TIMER_NAME_LENGTH = 15;

    private final View view;
    private AppState state;
    private final Timer periodicViewUpdateTask;

    private Controller(Stage appStage) {
        state = AppState.initState(INITIAL_TIMER_TYPES, INITIAL_TIMER_INDEX);
        view = new View.Builder(state, appStage)
                .onClosed(this::onWindowClosing)
                .registerClock(LocalDateTime.now())
                .registerDeleteModeAction(this::switchDeleteMode)
                .registerReportAction(this::showReport)
                .registerAddWorkTimerAction(() -> System.out.println("addWorkTimer pressed"))
                .registerAddBreakTimerAction(() -> System.out.println("addBreakTimer pressed"))
                .registerStartTimerAction(this::startTimer)
                .registerPauseTimerAction(this::pauseTimer)
                .registerSkipNextTimerAction(() -> System.out.println("skipNextTimer pressed"))
                .registerStopPomoAction(() -> System.out.println("stopPomo pressed"))
                .setValidatorForTimerName(validateTimerNameInput())
                .setValidatorForCountdownTime(validateCountdownTimeInput())
                .onInvalidInputForTimerName(input -> showInvalidTimerNameError(input))
                .onInvalidInputForCountdownTime(input -> showInvalidCountdownTimeError(input))
                .build();
        periodicViewUpdateTask = new Timer();
    }

    static Controller create(Stage appStage) {
        return new Controller(appStage);
    }

    void start() {
        setupPeriodicViewUpdate();
        view.show();
    }

    private void setupPeriodicViewUpdate() {
        periodicViewUpdateTask.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    view.updateClock(LocalDateTime.now());
//                    if (pomoCtrl.isActive()) {
//                        pomoCtrl.update();
//                    }
                });
            }
        }, 0, PERIODIC_VIEW_UPDATE_INTERVAL);
    }

    private void onWindowClosing() {
        periodicViewUpdateTask.cancel();
    }

    private void switchDeleteMode() {
        state = state.switchDeleteMode();
        if (state.isDeleteModeOn()) {
            view.activateTimerDeletionView();
        } else {
            view.deactivateTimerDeletionView();
        }
    }

    private void startTimer() {
        view.activateRunningView();
    }

    private void pauseTimer() {
        view.deactivateRunningView();
    }

    private void showReport() {
        view.showReport("hellooooooooo");
    }

    private void showInvalidTimerNameError(String input) {
        view.showReport("showInvalidTimerNameError " + input);
    }

    private void showInvalidCountdownTimeError(String input) {
        view.showReport("showInvalidCountdownTimeError  " + input);
    }

    private Predicate<String> validateCountdownTimeInput() {
        return input -> {
            try {
                int val = Integer.parseInt(input);
                if (val <= MAX_COUNTDOWN_TIME && val > 0) {
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    private Predicate<String> validateTimerNameInput() {
        return input -> input.length() <= MAX_TIMER_NAME_LENGTH;
    }


}
