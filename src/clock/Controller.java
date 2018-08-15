package clock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.function.Predicate;

import javafx.application.Platform;
import javafx.stage.Stage;

class Controller {

    private static final long PERIODIC_VIEW_UPDATE_INTERVAL = 500;

    private final View view;
    private AppState state;
    private final java.util.Timer periodicViewUpdateTask;

    private Controller(Stage appStage) {
        state = AppState.initState(InitialValues.TIMER_TYPES, InitialValues.TIMER_INDEX);
        view = new View.Builder(state, appStage)
                .onClosed(this::onWindowClosing)
                .registerClock(LocalDateTime.now())
                .registerDeleteModeAction(this::switchDeleteMode)
                .registerReportAction(this::createAndShowReport)
                .registerAddWorkTimerAction(() -> System.out.println("addWorkTimer pressed"))
                .registerAddBreakTimerAction(() -> System.out.println("addBreakTimer pressed"))
                .registerStartTimerAction(this::startCurrentTimer)
                .registerPauseTimerAction(this::pauseCurrentTimer)
                .registerSkipNextTimerAction(this::skipCurrentTimer)
                .registerStopPomoAction(this::stop)
                .setValidatorForTimerName(validateTimerNameInput())
                .setValidatorForCountdownTime(validateCountdownTimeInput())
                .onInvalidInputForTimerName(input -> showInvalidTimerNameError(input))
                .onInvalidInputForTimerDuration(input -> showInvalidTimerDurationError(input))
                .build();
        periodicViewUpdateTask = new java.util.Timer();
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
                    Timer currentTimer = state.currentTimer();
                    if (currentTimer.isRunning()) {
                        view.updateTimerTime(state.currentTimer().timeValues());
                    }
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

    private void startCurrentTimer() {
        state.currentTimer().start();
        view.activateRunningView();
    }

    private void pauseCurrentTimer() {
        state.currentTimer().pause();
        view.deactivateRunningView();
    }

    private void finishCurrentTimerSession() {
        if (state.currentTimer().isRunning()) {
            pauseCurrentTimer();
        }
        if (state.currentTimer().isPaused()) {
            state.currentTimer().clearCurrentSession();
            view.updateTimerTime(state.currentTimer().timeValues());
        }
    }

    private void skipCurrentTimer() {
        boolean oldTimerWasRunning = state.currentTimer().isRunning();
        finishCurrentTimerSession();
        selectTimer(nextTimerIndex());
        if (oldTimerWasRunning) {
            startCurrentTimer();
        }
    }

    private void selectTimer(int index) {
        state = state.setTimerIndex(index);
        view.selectTimer(state.currentTimerIndex());
    }

    private int nextTimerIndex() {
        int currentIndex = state.currentTimerIndex();
        if (currentIndex + 1 == state.timers().size()) {
            return 0;
        }
        return currentIndex + 1;
    }

    private void stop() {
        finishCurrentTimerSession();
        createAndShowReport();
        for (Timer timer: state.timers()) {
            timer.clearAll();
        }
        selectTimer(0);
    }

    private void createAndShowReport() {
        List<TimerReport> reports = createTimerReports();
        String contents = createReportContents(reports);
        view.showInfoDialogOnTop(DialogMessage.REPORT.setContent(contents));
    }

    private List<TimerReport> createTimerReports() {
        List<TimerReport> reports = new ArrayList<>(state.timers().size());
        for (clock.Timer timer: state.timers()) {
            reports.add(timer.report());
        }
        return reports;
    }

    private String createReportContents(List<TimerReport> reports) {
        StringBuilder contents = new StringBuilder();
        for (TimerReport report: reports) {
            contents.append(report.toString());
            contents.append("\n");
        }
        return contents.toString();
    }

    private void showInvalidTimerNameError(String input) {
        view.showInfoDialog(DialogMessage.INVALID_TIMER_NAME);
    }

    private void showInvalidTimerDurationError(String input) {
        view.showInfoDialog(DialogMessage.INVALID_TIMER_DURATION);
    }

    private Predicate<String> validateCountdownTimeInput() {
        return input -> {
            try {
                int val = Integer.parseInt(input);
                if (val <= LimitationValues.MAX_TIMER_DURATION_TARGET && val > 0) {
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        };
    }

    private Predicate<String> validateTimerNameInput() {
        return input -> input.length() <= LimitationValues.MAX_TIMER_NAME_LENGTH;
    }


}
