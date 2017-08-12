package clock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class CountdownTimer {
    private Duration passedTimeInRound;
    private LocalDateTime startTime;
    private int maxMinute;
    private boolean isActive = false;
    private ColorSet colorSet;

    private Label timerNameLabel;
    private PomodoroChart chart;
    private Circle donutHole;
    private Label remainingMinuteLabel;
    private VBox timerNode;

    public CountdownTimer(String timerName, int maxMinute, ColorSet colorSet) {
        this.maxMinute = maxMinute;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();
        this.colorSet = colorSet;

        timerNameLabel = new Label(timerName);

        donutHole = new Circle(100, Color.WHITESMOKE);
        donutHole.setStrokeWidth(0);
        chart = new PomodoroChart(maxMinute, 0, colorSet);
        donutHole.radiusProperty().bind(chart.heightProperty().multiply(0.35));
        remainingMinuteLabel = new Label(Integer.toString(remainingMinute()));
        remainingMinuteLabel.setTextFill(colorSet.remainingDimColor);
        remainingMinuteLabel.setFont(new Font(50));
        StackPane chartPane = new StackPane(chart, donutHole, remainingMinuteLabel);

        timerNode = new VBox(timerNameLabel, chartPane);
        timerNode.setAlignment(Pos.TOP_CENTER);
    }

    public Node getNode() {
        return timerNode;
    }

    public void start() {
        startTime = LocalDateTime.now();
        remainingMinuteLabel.setTextFill(colorSet.remainingColor);
        chart.brighterColor();
        isActive = true;
    }

    public void pause() {
        remainingMinuteLabel.setTextFill(colorSet.remainingDimColor);
        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
        chart.dimColor();
        isActive = false;
    }
    public void updateTimer() {
        int remaining = remainingMinute();
        int passed = passedMinute();
        remainingMinuteLabel.setText(Integer.toString(remaining));
        chart.setTimeValues(remaining, passed);
    }

    public boolean isActive() {
        return isActive;
    }

    private int remainingMinute() {
        return maxMinute- passedMinute();
    }

    private int passedMinute() {
        if(isActive) {
            Duration durationAfterStart = Duration.between(startTime, LocalDateTime.now());
            Duration totalDuration = passedTimeInRound.plus(durationAfterStart);
            System.out.println("isStarted" + totalDuration);
            return (int)totalDuration.toMinutes();
        } else {
            System.out.println("isNotStared" + passedTimeInRound);
            return (int)passedTimeInRound.toMinutes();
        }
    }

}
