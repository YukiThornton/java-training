package clock;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    DateTimeFormatter clockDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
    DateTimeFormatter clockTimeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
    LocalDateTime pomoStartTime = LocalDateTime.now();
    PieChart.Data remainingTimeData;
    PieChart.Data passedTimeData;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(4);
        Label clockDateLabel = new Label(clockDate());
        Label clockTimeLabel = new Label(clockTime());
        Label remainingMinuteLabel = new Label(Integer.toString(remainingMinute()));
        PomodoroChart pomoChart = new PomodoroChart(25, 0);
        clockDateLabel.setFont(new Font(50));
        clockTimeLabel.setFont(new Font(50));
        remainingMinuteLabel.setFont(new Font(50));
        box.getChildren().add(clockDateLabel);
        box.getChildren().add(clockTimeLabel);
        box.getChildren().add(remainingMinuteLabel);
        box.getChildren().add(pomoChart);

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
                    int remaining = remainingMinute();
                    int passed = passedMinute();
                    remainingMinuteLabel.setText(Integer.toString(remaining));
                    pomoChart.setTimeValues(remaining, passed);
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
        return (int)Duration.between(pomoStartTime, LocalDateTime.now()).toMinutes();
    }

}
