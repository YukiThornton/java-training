package clock;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.imageio.plugins.common.BogusColorSpace;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    DateTimeFormatter clockDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
    DateTimeFormatter clockTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    PieChart.Data remainingTimeData;
    PieChart.Data passedTimeData;
    Duration pomoPassedTime = Duration.of(0, ChronoUnit.SECONDS);
    LocalDateTime pomoStartTime = LocalDateTime.now();
    boolean isStarted = false;
    private Circle innerCircle;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(4);
        Label clockDateLabel = new Label(clockDate());
        Label clockTimeLabel = new Label(clockTime());
        innerCircle = new Circle(100, Color.WHITESMOKE);
        innerCircle.setStrokeWidth(0);
        PomodoroChart pomoChart = new PomodoroChart(25, 0);
        innerCircle.radiusProperty().bind(pomoChart.heightProperty().multiply(0.35));
        Label remainingMinuteLabel = new Label(Integer.toString(remainingMinute()));
        remainingMinuteLabel.setTextFill(Color.LIGHTGRAY);
        StackPane chartPane = new StackPane(pomoChart, innerCircle, remainingMinuteLabel);
        Label switchLabel = new Label("start");
        switchLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isStarted) {
                    switchLabel.setText("start");
                    remainingMinuteLabel.setTextFill(Color.LIGHTGRAY);
                    pomoPassedTime = pomoPassedTime.plus(Duration.between(pomoStartTime, LocalDateTime.now()));
                    isStarted = false;
                    pomoChart.dimColor();
                } else {
                    switchLabel.setText("pause");
                    pomoStartTime = LocalDateTime.now();
                    remainingMinuteLabel.setTextFill(Color.CORNFLOWERBLUE);
                    isStarted = true;
                    pomoChart.brighterColor();
                }
            }
        });
        clockDateLabel.setFont(new Font(50));
        clockTimeLabel.setFont(new Font(50));
        switchLabel.setFont(new Font(50));
        remainingMinuteLabel.setFont(new Font(50));
        box.getChildren().add(clockDateLabel);
        box.getChildren().add(clockTimeLabel);
        box.getChildren().add(chartPane);
        box.getChildren().add(switchLabel);

        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle("Pomo");
        primaryStage.show();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    clockDateLabel.setText(clockDate());
                    clockTimeLabel.setText(clockTime());
                    if (isStarted) {
                        int remaining = remainingMinute();
                        int passed = passedMinute();
                        remainingMinuteLabel.setText(Integer.toString(remaining));
                        pomoChart.setTimeValues(remaining, passed);
                    }
                });
            }
        }, 0, 500);
        primaryStage.setOnCloseRequest(evet -> {
            timer.cancel();
        });
    }

    private String clockDate() {
        return LocalDate.now().format(clockDateFormatter);
    }

    private String clockTime() {
        return LocalTime.now().format(clockTimeFormatter);
    }

    private int remainingMinute() {
        return 25- passedMinute();
    }

    private int passedMinute() {
        if(isStarted) {
            Duration durationAfterStart = Duration.between(pomoStartTime, LocalDateTime.now());
            Duration totalDuration = pomoPassedTime.plus(durationAfterStart);
            System.out.println("isStarted" + totalDuration);
            return (int)totalDuration.toMinutes();
        } else {
            System.out.println("isNotStared" + pomoPassedTime);
            return (int)pomoPassedTime.toMinutes();
        }
    }

}
