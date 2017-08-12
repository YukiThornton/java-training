package clock;

import java.util.ArrayList;
import java.util.List;
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
    private List<CountdownTimer> timers;
    private CountdownTimer workTimer;
    private CountdownTimer restTimer;
    private int currentTimerIndex;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        clock = new Clock();
        workTimer = new CountdownTimer("work", 25, ColorSet.BLUE);
        restTimer = new CountdownTimer("rest", 5, ColorSet.YELLOW);
        timers = new ArrayList<>();
        timers.add(workTimer);
        timers.add(restTimer);
        currentTimerIndex = 1;
        HBox timerBox = new HBox(workTimer.getNode(), restTimer.getNode());

        Label switchLabel = new Label(BTN_TXT_START);
        switchLabel.setFont(new Font(50));
        switchLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isStarted()) {
                    switchLabel.setText(BTN_TXT_START);
                    timers.get(currentTimerIndex).pause();
                } else {
                    switchLabel.setText(BTN_TXT_PAUSE);
                    timers.get(currentTimerIndex).start();
                }
            }
        });
        VBox box = new VBox(4);
        box.getChildren().add(clock.getDateNode());
        box.getChildren().add(clock.getTimeNode());
        box.getChildren().add(timerBox);
        box.getChildren().add(switchLabel);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    clock.update();
                    if (isStarted()) {
                        timers.get(currentTimerIndex).updateTimer();
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

    private boolean isStarted() {
        return timers.get(currentTimerIndex).isActive();
    }
}
