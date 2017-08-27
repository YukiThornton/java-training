package clock;

import static clock.NodeTools.FONT_MEDIUM;
import static clock.NodeTools.FONT_SMALL;
import static clock.NodeTools.createHBox;
import static clock.NodeTools.createIconBtn;
import static clock.NodeTools.createVBox;
import static clock.NodeTools.wrapWithScrollPane;
import static clock.NodeTools.showAlertAndWait;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import clock.CountdownTimer.TimerPurpose;
import clock.CountdownTimer.TimerType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String BTN_TXT_START = "\uE038";
    private static final String BTN_TXT_PAUSE = "\uE035";
    private static final String BTN_TXT_SKIP = "\uE044";
    private static final String BTN_TXT_STOP = "\uE047";
    private static final String BTN_TXT_ADD_TIMER = "\uE856";
    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 350;
    private static final double WINDOW_MIN_HEIGHT = 665;
    private static final String ALERT_SWITCH_TIMERS_TITLE = "Time's up!";
    private static final String ALERT_SWITCH_TIMERS_CONTENT = "Time to ";
    private static final String ALERT_MAX_MINUTE_INPUT_ERROR_TITLE = "You can't!";
    private static final String ALERT_MAX_MINUTE_INPUT_ERROR_CONTENT = "You cannot set bigger than 1000 minutes.";
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
    private HBox timerBox;
    private BorderPane rootBox;
    private Label startOrPauseBtn;
    private Label skipBtn;
    private Label stopBtn;
    private Label addWorkTimerBtn;
    private Label addRestTimerBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        pomoCtrl = createPomoCtrl();
        ColorSet currentColorSet = pomoCtrl.currentTimer().getColorSet();
        clock = new Clock(FONT_SMALL, currentColorSet.remainingDimColor);

        timerBox = createHBox(Pos.CENTER, pomoCtrl.getNodes());
        ScrollPane timerScrlPane = wrapWithScrollPane(timerBox);
        timerScrlPane.setStyle("-fx-background-color:transparent;");
        timerScrlPane.getStyleClass().add("scroll-pane");

        startOrPauseBtn = createStartOrPauseBtn(currentColorSet);
        skipBtn = createSkipBtn(currentColorSet);
        stopBtn = createStopBtn(currentColorSet);
        addWorkTimerBtn = createAddWorkTimerBtn();
        addRestTimerBtn = createAddRestTimerBtn();
        HBox ctrlBtns = createHBox(Pos.CENTER, stopBtn, startOrPauseBtn, skipBtn);
        HBox timerBtns = createHBox(Pos.CENTER_RIGHT, addWorkTimerBtn, addRestTimerBtn);

        rootBox = new BorderPane();
        rootBox.setPadding(new Insets(10, 20, 10, 10));
        rootBox.setTop(createHBox(null, clock.getDateNode()));
        rootBox.setCenter(createVBox(Pos.CENTER, clock.getTimeNode(), timerScrlPane, ctrlBtns));
        rootBox.setBottom(createVBox(null, timerBtns));

        Scene scene = new Scene(rootBox);
        scene.getStylesheets().add("clock/css/main.css");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

        changeBgColor(pomoCtrl.currentTimer().getColorSet());

        timer = createAndSetupTimer();

        setUpStage(primaryStage, scene);
        primaryStage.show();
        initialized = true;
    }

    private PomodoroController createPomoCtrl() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        PomodoroController pomoCtrl = new PomodoroController();
        pomoCtrl.onSwitchTimers((oldTimer, newTimer) -> {
            Toolkit.getDefaultToolkit().beep();
            showSwitchTimerAlert(newTimer.getTimerPurpose());
            changeColors(newTimer.getColorSet());
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

    private Label createStartOrPauseBtn(ColorSet colorSet) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label btn = createIconBtn(BTN_TXT_START, FONT_MEDIUM, colorSet.remainingDimColor);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoCtrlBtn();
            }
        });
        return btn;
    }

    private Label createSkipBtn(ColorSet colorSet) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label btn = createIconBtn(BTN_TXT_SKIP, FONT_MEDIUM, colorSet.remainingDimColor);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickSkipBtn();
            }
        });
        return btn;
    }

    private Label createStopBtn(ColorSet colorSet) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label btn = createIconBtn(BTN_TXT_STOP, FONT_MEDIUM, colorSet.remainingDimColor);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoResetBtn();
            }
        });
        return btn;
    }

    private Label createAddWorkTimerBtn() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label btn = createIconBtn(BTN_TXT_ADD_TIMER, FONT_MEDIUM, ColorSet.BLUE.remainingDimColor);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddTimerBtn();
            }
        });
        return btn;
    }

    private Label createAddRestTimerBtn() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label btn = createIconBtn(BTN_TXT_ADD_TIMER, FONT_MEDIUM, ColorSet.YELLOW.remainingDimColor);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddRestBtn();
            }
        });
        return btn;
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
        stage.setTitle("Pairdoro Timer");
        stage.setOnCloseRequest(evet -> {
            timer.cancel();
        });
        stage.setHeight(WINDOW_PREF_HEIGHT);
        stage.setWidth(WINDOW_PREF_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
    }

    private void switchTxtBtnColors(Color color) {
        startOrPauseBtn.setTextFill(color);
        stopBtn.setTextFill(color);
        skipBtn.setTextFill(color);
        clock.changeTextColor(color);
    }

    private void onClickPomoCtrlBtn() {
        if (pomoCtrl.isActive()) {
            startOrPauseBtn.setText(BTN_TXT_START);
            stopBtn.setVisible(true);
            pomoCtrl.pause();
        } else {
            startOrPauseBtn.setText(BTN_TXT_PAUSE);
            stopBtn.setVisible(false);
            pomoCtrl.start();
        }
    }

    private void changeBgColor(ColorSet colorSet) {
        rootBox.setStyle("-fx-background-color:" + colorSet.whitish);
    }

    private void onClickSkipBtn() {
        if (pomoCtrl.isActive()) {
            pomoCtrl.switchAndStart();
        } else {
            pomoCtrl.switchTimers();
        }
        changeColors(pomoCtrl.currentTimer().getColorSet());
    }

    private void changeColors(ColorSet colorSet) {
        changeBgColor(colorSet);
        switchTxtBtnColors(colorSet.remainingDimColor);
    }

    private void onClickPomoResetBtn() {
        pomoCtrl.reset();
    }

    private void onClickPomoAddTimerBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            showTooManyTimerAlert();
            return;
        }
        Platform.runLater(() -> {
            timerBox.getChildren().add(pomoCtrl.createNewTimer(TimerType.WORK_BLUE));
        });
    }

    private void onClickPomoAddRestBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            showTooManyTimerAlert();
            return;
        }
        Platform.runLater(() -> {
            timerBox.getChildren().add(pomoCtrl.createNewTimer(TimerType.REST_YELLOW));
        });
    }

    private void showSwitchTimerAlert(TimerPurpose purpose) {
        showAlertAndWait(ALERT_SWITCH_TIMERS_TITLE, ALERT_SWITCH_TIMERS_CONTENT + purpose.verb(), AlertType.INFORMATION, true);
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
