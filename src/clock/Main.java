package clock;

import java.util.Timer;
import java.util.TimerTask;

import clock.CountdownTimer.TimerType;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private static final String BTN_TXT_START = "Start";
    private static final String BTN_TXT_PAUSE = "Pause";
    private static final String BTN_TXT_RESET = "Reset";

    private Clock clock;
    private PomodoroController pomoCtrl;
    private HBox timerBox;
    private Label pomoCtrlBtn;
    private Label pomoResetBtn;
    private Label pomoAddTimerBtn;
    private Label pomoAddRestBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        clock = new Clock();
        pomoCtrl = new PomodoroController();
        timerBox = new HBox(pomoCtrl.getNodes());

        pomoCtrlBtn = new Label(BTN_TXT_START);
        pomoCtrlBtn.setFont(new Font(50));
        pomoCtrlBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoCtrlBtn();
            }
        });

        pomoResetBtn = new Label(BTN_TXT_RESET);
        pomoResetBtn.setFont(new Font(50));
        pomoResetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoResetBtn();
            }
        });

        pomoAddTimerBtn = new Label("add work");
        pomoAddTimerBtn.setFont(new Font(50));
        pomoAddTimerBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddTimerBtn();
            }
        });
        pomoAddRestBtn = new Label("add rest");
        pomoAddRestBtn.setFont(new Font(50));
        pomoAddRestBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoAddRestBtn();
            }
        });
        HBox btns = new HBox(pomoCtrlBtn, pomoResetBtn, pomoAddTimerBtn, pomoAddRestBtn);
        btns.setSpacing(20);

        VBox box = new VBox(4);
        box.getChildren().add(clock.getDateNode());
        box.getChildren().add(clock.getTimeNode());
        box.getChildren().add(timerBox);
        box.getChildren().add(btns);

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

        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle("Pomo");
        primaryStage.show();
    }

    private void onClickPomoCtrlBtn() {
        if (pomoCtrl.isActive()) {
            pomoCtrlBtn.setText(BTN_TXT_START);
            pomoResetBtn.setVisible(true);
            pomoCtrl.pause();
        } else {
            pomoCtrlBtn.setText(BTN_TXT_PAUSE);
            pomoResetBtn.setVisible(false);
            pomoCtrl.start();
        }
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
            timerBox.getChildren().add(pomoCtrl.createNewTimer(TimerType.WORK_BLUE));
        });
    }

    private void onClickPomoAddRestBtn() {
        if (!pomoCtrl.canAddMoreTimer()) {
            // TODO Dialog
            throw new IllegalStateException();
        }
        Platform.runLater(() -> {
            timerBox.getChildren().add(pomoCtrl.createNewTimer(TimerType.REST_YELLOW));
        });
    }
}
