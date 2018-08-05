package clock;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.stage.Stage;

class Controller {

    private static final TimerType[] INITIAL_TIMER_TYPES = {TimerType.WORK_DEFAULT, TimerType.BREAK_DEFAULT};
    private static final int INITIAL_TIMER_INDEX = 0;
    private static final long PERIODIC_VIEW_UPDATE_INTERVAL = 500;

    private final View view;
    private AppState state;
    private final Timer periodicViewUpdateTask;

    private Controller(Stage appStage) {
        state = AppState.initState(INITIAL_TIMER_TYPES, INITIAL_TIMER_INDEX);
        view = new View.Builder(state, appStage)
                .onClosed(this::onWindowClosing)
                .registerClock(LocalDateTime.now())
                .registerDeleteModeAction(this::onDeleteBtnClicked)
                .registerReportAction(this::onReportBtnClicked)
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

    private void onDeleteBtnClicked() {
        state = state.switchDeleteMode();
    }

    private void onReportBtnClicked() {
        view.showReport("hellooooooooo");
        state = state.switchDeleteMode();
    }

}
