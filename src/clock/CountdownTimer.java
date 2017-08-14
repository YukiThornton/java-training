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
    private Duration passedTimeInTotal;
    private LocalDateTime startTime;
    private int maxSeconds;
    private boolean isActive = false;
    private ColorSet colorSet;

    private Label timerNameLabel;
    private TimerChart chart;
    private Circle donutHole;
    private Label remainingMinuteLabel;
    private VBox timerNode;

    public CountdownTimer(String timerName, int maxMinute, ColorSet colorSet) {
        this.maxSeconds = maxMinute * 60;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();
        this.colorSet = colorSet;

        timerNameLabel = new Label(timerName);

        donutHole = new Circle(100, Color.WHITESMOKE);
        donutHole.setStrokeWidth(0);
        chart = new TimerChart(maxMinute, 0, colorSet);
        donutHole.radiusProperty().bind(chart.heightProperty().multiply(0.35));
        remainingMinuteLabel = new Label(toRemainingText());
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
        System.out.println("start" + timerNameLabel.textProperty().get());
    }

    public void pause() {
        remainingMinuteLabel.setTextFill(colorSet.remainingDimColor);
        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
        chart.dimColor();
        isActive = false;
        System.out.println("pause" + timerNameLabel.textProperty().get());
    }

    public void saveAndReset() {
        passedTimeInTotal = passedTimeInTotal.plus(passedTimeInRound);
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();
        int remaining = remainingSeconds();
        remainingMinuteLabel.setText(Integer.toString(remaining / 60));
        chart.setTimeValues(remaining, passedSeconds());
    }

    public void update() {
        int passed = passedSeconds();
        int remaining = remainingSeconds(passed);
        remainingMinuteLabel.setText(toRemainingText(remaining));
        chart.setTimeValues(remaining, passed);
    }

    public boolean shouldReset() {
        return remainingSeconds() < 0;
    }

    public boolean isActive() {
        return isActive;
    }

    private int remainingSeconds() {
        return maxSeconds- passedSeconds();
    }

    private int remainingSeconds(int passedSeconds) {
        return maxSeconds- passedSeconds;
    }

    private String toRemainingText() {
        return toRemainingText(remainingSeconds());
    }

    private String toRemainingText(int remainingSeconds) {
        if (remainingSeconds < 60) {
            return "0:" + remainingSeconds;
        } else {
            return Integer.toString(remainingSeconds / 60);
        }
    }

    private int passedSeconds() {
        if(isActive) {
            Duration durationAfterStart = Duration.between(startTime, LocalDateTime.now());
            Duration totalDuration = passedTimeInRound.plus(durationAfterStart);
            System.out.println("isStarted" + totalDuration);
            return (int)totalDuration.getSeconds();
        } else {
            return (int)passedTimeInRound.getSeconds();
        }
    }

}
