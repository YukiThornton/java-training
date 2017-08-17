package clock;

import static clock.NodeTools.*;

import java.util.Timer;
import java.util.TimerTask;

import clock.CountdownTimer.TimerType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String BTN_TXT_START = "\uE038";
    private static final String BTN_TXT_PAUSE = "\uE035";
    private static final String BTN_TXT_SKIP = "\uE044";
    private static final String BTN_TXT_STOP = "\uE047";
    private static final String BTN_TXT_ADD_TIMER = "\uE856";

    private Clock clock;
    private PomodoroController pomoCtrl;
    private HBox timerBox;
    private VBox rootBox;
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
        pomoCtrl = new PomodoroController();
        pomoCtrl.onSwitchTimers((newTimer) -> {
            changeColors(newTimer.getColorSet());
        });
        pomoCtrl.onTimerDeleted(timer -> {
            timerBox.getChildren().clear();
            timerBox.getChildren().addAll(pomoCtrl.getNodes());
        });
        ColorSet currentColorSet = pomoCtrl.currentTimer().getColorSet();
        clock = new Clock(FONT_SMALL, currentColorSet.remainingDimColor);

        rootBox = new VBox(4);
        Scene scene = new Scene(rootBox);
        scene.getStylesheets().add("clock/css/main.css");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

        timerBox = new HBox(pomoCtrl.getNodes());
        ScrollPane sp = new ScrollPane(timerBox);
        sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollBarPolicy.NEVER);

        startOrPauseBtn = createIconBtn(BTN_TXT_START, FONT_MEDIUM, currentColorSet.remainingDimColor);
        startOrPauseBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoCtrlBtn();
            }
        });

        skipBtn = createIconBtn(BTN_TXT_SKIP, FONT_MEDIUM, currentColorSet.remainingDimColor);
        skipBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickSkipBtn();
            }
        });
        stopBtn = createIconBtn(BTN_TXT_STOP, FONT_MEDIUM, currentColorSet.remainingDimColor);
        stopBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoResetBtn();
            }
        });
        addWorkTimerBtn = createIconBtn(BTN_TXT_ADD_TIMER, FONT_MEDIUM, ColorSet.BLUE.remainingDimColor);
        addWorkTimerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddTimerBtn();
            }
        });
        addRestTimerBtn = createIconBtn(BTN_TXT_ADD_TIMER, FONT_MEDIUM, ColorSet.YELLOW.remainingDimColor);
        addRestTimerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddRestBtn();
            }
        });
        HBox btns = new HBox(startOrPauseBtn, skipBtn, stopBtn, addWorkTimerBtn, addRestTimerBtn);
        btns.setSpacing(20);

        rootBox.getChildren().add(clock.getDateNode());
        rootBox.getChildren().add(clock.getTimeNode());
        rootBox.getChildren().add(sp);
        rootBox.getChildren().add(btns);
        changeBgColor(pomoCtrl.currentTimer().getColorSet());
        

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    clock.update();
                    if (pomoCtrl.isActive()) {
                        pomoCtrl.update();
                    }
                });
            }
        }, 0, 500);
        primaryStage.setOnCloseRequest(evet -> {
            timer.cancel();
        });

        sp.setStyle("-fx-background-color:transparent;");
        sp.getStyleClass().add("scroll-pane");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pomo");
        primaryStage.show();
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
            // TODO Dialog
            throw new IllegalStateException();
        }
        Platform.runLater(() -> {
            timerBox.getChildren().add(pomoCtrl.createNewTimer("work", 25, TimerType.WORK_BLUE));
        });
    }

    private void onClickPomoAddRestBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            // TODO Dialog
            throw new IllegalStateException();
        }
        Platform.runLater(() -> {
            timerBox.getChildren().add(pomoCtrl.createNewTimer("reset", 5, TimerType.REST_YELLOW));
        });
    }
}
