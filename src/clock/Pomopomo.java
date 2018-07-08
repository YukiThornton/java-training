package clock;

import static clock.NodeTools.ensureVisibleInScrollPane;
import static clock.NodeTools.showAlertAndWait;

import java.awt.Toolkit;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Pomopomo extends Application {

    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 340;
    private static final double WINDOW_MIN_HEIGHT = 535;
    private static final ColorPalette BG_COLORS = ColorPalette.GRAY;
    private static final ColorPalette.Key BG_COLOR_KEY = ColorPalette.Key.LIGHT;
    private static final String INFO_REPORT_TITLE = "Good job!";
    private static final String ALERT_SWITCH_TIMERS_TITLE = "Time's up!";
    private static final String ALERT_SWITCH_TIMERS_CONTENT = "Time to ";
    private static final String ALERT_MAX_MINUTE_INPUT_ERROR_TITLE = "You can't!";
    private static final String ALERT_MAX_MINUTE_INPUT_ERROR_CONTENT = "Set time between 1 and 999.";
    private static final String ALERT_TIMER_NAME_INPUT_ERROR_TITLE = "You can't!";
    private static final String ALERT_TIMER_NAME_INPUT_ERROR_CONTENT = "Too long!";
    private static final String ALERT_TOO_MANY_TIMERS_TITLE = "You can't!";
    private static final String ALERT_TOO_MANY_TIMERS_CONTENT = "You cannot have more than 10 timers.";
    private static final String ALERT_TIMER_DELETE_REJECT_TITLE = "You can't!";
    private static final String ALERT_TIMER_DELETE_REJECT_CONTENT = "You MUST have at least 1 work timer and 1 rest timer.";

    private Clock clock;
    private PomodoroController pomoCtrl;
    private Timer timer;
    private boolean initialized = false;
    private boolean isDeleting = false;
    private ColorPalette currentPalette;
    private HBox timerBox;
    private ScrollPane timerScrlPane;
    private BorderPane rootBox;
    private Label dateLabel;
    private Label timeLabel;
    private Label trashBtn;
    private Label reportBtn;
    private Label startBtn;
    private Label pauseBtn;
    private Label skipBtn;
    private Label stopBtn;
    private Label addWorkTimerBtn;
    private Label addBreakTimerBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = Controller.create(primaryStage);
        controller.start();
//        if (initialized) {
//            throw new IllegalStateException("Already initialized.");
//        }
//        pomoCtrl = createPomoCtrl();
//        currentPalette = pomoCtrl.currentTimer().getColorPalette();
//        clock = new Clock(AppFont.TEXT_20, AppFont.TEXT_30, currentPalette);
//
//        timerBox = createHBox(Pos.CENTER, pomoCtrl.getNodes());
//        timerScrlPane = wrapWithScrollPane(timerBox);
//        timerScrlPane.setStyle("-fx-background-color:transparent;");
//        timerScrlPane.getStyleClass().add("scroll-pane");
//        timeLabel = clock.getTimeNode();
//
//        trashBtn = createDeleteBtn();
//        trashBtn.setPadding(new Insets(0, 15, 0, 0));
//        reportBtn = createReportBtn();
//        BorderPane topBox = new BorderPane();
//        dateLabel = clock.getDateNode();
//        topBox.setLeft(dateLabel);
//        topBox.setRight(createHBox(Pos.TOP_RIGHT, trashBtn, reportBtn));
//
//        startBtn = createStartBtn();
//        pauseBtn = createPauseBtn();
//        skipBtn = createSkipBtn();
//        stopBtn = createStopBtn();
//        addWorkTimerBtn = createAddWorkTimerBtn();
//        addBreakTimerBtn = createAddBreakTimerBtn();
//        HBox ctrlBtns = createHBox(Pos.CENTER, stopBtn, startBtn, pauseBtn, skipBtn);
//        ctrlBtns.setSpacing(30);
//
//        HBox timerBtns = createHBox(Pos.CENTER_RIGHT, addWorkTimerBtn, addBreakTimerBtn);
//        VBox bottomBox = createVBox(null, timerBtns);
//
//        rootBox = new BorderPane();
//        rootBox.setPadding(new Insets(10, 20, 10, 10));
//        rootBox.setTop(topBox);
//        rootBox.setCenter(createVBox(Pos.CENTER, timeLabel, timerScrlPane, ctrlBtns));
//        rootBox.setBottom(bottomBox);
//        rootBox.widthProperty().addListener((observable, oldValue, newValue) -> {
//            double btnWidth = addBreakTimerBtn.getWidth() + skipBtn.getWidth() + stopBtn.getWidth() + (addWorkTimerBtn.getWidth() + addBreakTimerBtn.getWidth()) * 2;
//            if (btnWidth >= rootBox.getWidth()) {
//                hideNode(timerBtns, false);
//            } else {
//                hideNode(timerBtns, true);
//            }
//        });
//        rootBox.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (rootBox.getHeight() <= WINDOW_MIN_HEIGHT - 10) {
//                hideNode(topBox, false);
//                hideNode(bottomBox, false);
//            } else {
//                hideNode(topBox, true);
//                hideNode(bottomBox, true);
//            }
//        });
//
//        Scene scene = new Scene(rootBox);
//        scene.getStylesheets().add("clock/css/main.css");
//
//        rootBox.setStyle("-fx-background-color: " + BG_COLORS.getTextOf(BG_COLOR_KEY) + ";");
//
//        timer = createAndSetupTimer();
//
//        setUpStage(primaryStage, scene);
//        primaryStage.show();
//        initialized = true;
    }

    private PomodoroController createPomoCtrl() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        PomodoroController pomoCtrl = new PomodoroController(BG_COLORS.get(BG_COLOR_KEY));
        pomoCtrl.onTimerFinished((oldTimer, newTimer) -> {
            Toolkit.getDefaultToolkit().beep();
            showSwitchTimerAlert(newTimer.getTimerType());
            pomoCtrl.deselectActiveTimer();
            selectNextTimer();
            pomoCtrl.start();
            ensureVisibleInScrollPane(timerScrlPane, newTimer.getNode());
        });
        pomoCtrl.onInvalidInputForMaxMinute(t -> showMaxMinuteInputErrorAlert());
        pomoCtrl.onInvalidInputForTimerName(t -> showTimerNameInputErrorAlert());
        pomoCtrl.onTimerDeleteBtnSelected(timer -> {
            if (!pomoCtrl.canDeleteTimer(timer)) {
                showTimerDeleteRejectionAlert();
                return;
            }
            pomoCtrl.deleteTimer(timer);
        });
        pomoCtrl.onTimerDeleted(timer -> {
            timerBox.getChildren().clear();
            timerBox.getChildren().addAll(pomoCtrl.getNodes());
        });
        return pomoCtrl;
    }

    private Label createReportBtn() {
        IconButton.REPORT.setOnMouseClicked(event -> onClickReportBtn());
        return IconButton.REPORT.get();
    }

    private Label createDeleteBtn() {
        IconButton.DELETE.setOnMouseClicked(event -> onClickTrashBtn());
        return IconButton.DELETE.get();
    }

    private Label createStartBtn() {
        IconButton.START.setOnMouseClicked(event -> onClickPomoCtrlBtn());
        return IconButton.START.get();
    }

    private Label createPauseBtn() {
        IconButton.PAUSE.setOnMouseClicked(event -> onClickPomoCtrlBtn());
        return IconButton.PAUSE.get();
    }

    private Label createSkipBtn() {
        IconButton.SKIP.setOnMouseClicked(event -> onClickSkipBtn());
        return IconButton.SKIP.get();
    }

    private Label createStopBtn() {
        IconButton.STOP.setOnMouseClicked(event -> onClickPomoResetBtn());
        return IconButton.STOP.get();
    }

    private Label createAddWorkTimerBtn() {
        IconButton.ADD_WORK_TIMER.setOnMouseClicked(event -> onClickPomoAddTimerBtn());
        return IconButton.ADD_WORK_TIMER.get();
    }

    private Label createAddBreakTimerBtn() {
        IconButton.ADD_BREAK_TIMER.setOnMouseClicked(event -> onClickPomoAddRestBtn());
        return IconButton.ADD_BREAK_TIMER.get();
    }

    private Timer createAndSetupTimer() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (clock == null || pomoCtrl == null) {
                        throw new IllegalStateException("Not ready yet.");
                    }
                    clock.update();
                    if (pomoCtrl.isActive()) {
                        pomoCtrl.update();
                    }
                });
            }
        }, 0, 500);
        return timer;
    }

    private void setUpStage(Stage stage, Scene scene) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        stage.setScene(scene);
        stage.setTitle("Pomopomo Timer");
        stage.setOnCloseRequest(evet -> {
            timer.cancel();
        });
        stage.setHeight(WINDOW_PREF_HEIGHT);
        stage.setWidth(WINDOW_PREF_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
    }

    private void onClickTrashBtn() {
        if (isDeleting) {
            hideOrShowOnDeletingMode(false);
            isDeleting = false;
        } else {
            hideOrShowOnDeletingMode(true);
            isDeleting = true;
        }
    }

    private void hideOrShowOnDeletingMode(boolean hide) {
        reportBtn.setVisible(!hide);
        pomoCtrl.setVisibleOnDeleteBtn(hide);
        dateLabel.setVisible(!hide);
        timeLabel.setVisible(!hide);
        stopBtn.setVisible(!hide);
        startBtn.setVisible(!hide);
        pauseBtn.setVisible(!hide);
        skipBtn.setVisible(!hide);
        addWorkTimerBtn.setVisible(!hide);
        addBreakTimerBtn.setVisible(!hide);
    }

    private void onClickReportBtn() {
        showTimerReport(pomoCtrl.getReports());
    }

    private void onClickPomoCtrlBtn() {
        if (pomoCtrl.isActive()) {
            startBtn.setVisible(true);
            pauseBtn.setVisible(false);
            stopBtn.setVisible(true);
            pomoCtrl.pause();
        } else {
            startBtn.setVisible(false);
            pauseBtn.setVisible(true);
            stopBtn.setVisible(false);
            pomoCtrl.start();
        }
    }

    private void onClickSkipBtn() {
        if (pomoCtrl.isActive()) {
            pomoCtrl.pause();
            resetAndDeselectCurrentTimer();
            selectNextTimer();
            pomoCtrl.start();
        } else {
            resetAndDeselectCurrentTimer();
            selectNextTimer();
        }
    }

    private void resetAndDeselectCurrentTimer() {
        pomoCtrl.reset();
        pomoCtrl.deselectCurrent();
    }

    private void selectNextTimer() {
        pomoCtrl.selectNext();
        changeColors(pomoCtrl.currentTimer().getColorPalette());
    }

    private void selectTimer(int at) {
        pomoCtrl.select(at);
        changeColors(pomoCtrl.currentTimer().getColorPalette());
    }

    private void changeColors(ColorPalette palette) {
        currentPalette = palette;
        startBtn.setTextFill(currentPalette.get(ColorPalette.Key.LIGHT));
        pauseBtn.setTextFill(currentPalette.get(ColorPalette.Key.LIGHT));
        stopBtn.setTextFill(currentPalette.get(ColorPalette.Key.LIGHT));
        skipBtn.setTextFill(currentPalette.get(ColorPalette.Key.LIGHT));
        clock.changeTextColor(currentPalette);
    }

    private void onClickPomoResetBtn() {
        resetAndDeselectCurrentTimer();
        showTimerReport(pomoCtrl.getReports());
        pomoCtrl.clearAllHistory();
        selectTimer(0);
    }

    private void onClickPomoAddTimerBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            showTooManyTimerAlert();
            return;
        }
        Platform.runLater(() -> {
            Node newTimer = pomoCtrl.createNewTimer(TimerType.WORK_DEFAULT, BG_COLORS.get(BG_COLOR_KEY));
            timerBox.getChildren().add(newTimer);
            ensureVisibleInScrollPane(timerScrlPane, newTimer);
        });
    }

    private void onClickPomoAddRestBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            showTooManyTimerAlert();
            return;
        }
        Platform.runLater(() -> {
            Node newTimer = pomoCtrl.createNewTimer(TimerType.BREAK_DEFAULT, BG_COLORS.get(BG_COLOR_KEY));
            timerBox.getChildren().add(newTimer);
            ensureVisibleInScrollPane(timerScrlPane, newTimer);
        });
    }

    private void showTimerReport(List<TimerReport> reports) {
        String reportStr = "";
        for (int i = 0; i < reports.size(); i++) {
            reportStr += reports.get(i).toString() + "\n";
        }
        showAlertAndWait(INFO_REPORT_TITLE, reportStr, AlertType.INFORMATION, true);
    }

    private void showSwitchTimerAlert(TimerType timerType) {
        showAlertAndWait(ALERT_SWITCH_TIMERS_TITLE, ALERT_SWITCH_TIMERS_CONTENT + timerType.verbPhrase(), AlertType.INFORMATION, true);
    }

    private void showMaxMinuteInputErrorAlert() {
        showAlertAndWait(ALERT_MAX_MINUTE_INPUT_ERROR_TITLE, ALERT_MAX_MINUTE_INPUT_ERROR_CONTENT, AlertType.INFORMATION, false);
    }

    private void showTimerNameInputErrorAlert() {
        showAlertAndWait(ALERT_TIMER_NAME_INPUT_ERROR_TITLE, ALERT_TIMER_NAME_INPUT_ERROR_CONTENT, AlertType.INFORMATION, false);
    }

    private void showTooManyTimerAlert() {
        showAlertAndWait(ALERT_TOO_MANY_TIMERS_TITLE, ALERT_TOO_MANY_TIMERS_CONTENT, AlertType.INFORMATION, false);
    }

    private void showTimerDeleteRejectionAlert() {
        showAlertAndWait(ALERT_TIMER_DELETE_REJECT_TITLE, ALERT_TIMER_DELETE_REJECT_CONTENT, AlertType.INFORMATION, false);
    }
}
