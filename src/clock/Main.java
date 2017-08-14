package clock;

import java.util.Timer;
import java.util.TimerTask;

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

    private Clock clock;
    private PomodoroController pomoCtrl;
    private Label pomoCtrlBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        clock = new Clock();
        pomoCtrl = new PomodoroController();
        HBox timerBox = new HBox(pomoCtrl.getNodes());

        pomoCtrlBtn = new Label(BTN_TXT_START);
        pomoCtrlBtn.setFont(new Font(50));
        pomoCtrlBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                onClickPomoCtrlBtn();
            }
        });

        VBox box = new VBox(4);
        box.getChildren().add(clock.getDateNode());
        box.getChildren().add(clock.getTimeNode());
        box.getChildren().add(timerBox);
        box.getChildren().add(pomoCtrlBtn);

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
            pomoCtrl.pause();
        } else {
            pomoCtrlBtn.setText(BTN_TXT_PAUSE);
            pomoCtrl.start();
        }
    }
}
