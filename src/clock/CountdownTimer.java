package clock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class CountdownTimer {
    private Duration passedTimeInRound;
    private Duration passedTimeInTotal;
    private LocalDateTime startTime;
    private int passedSeconds;
    private int maxSeconds;
    private String timerName;
    private boolean isActive = false;
    private ColorSet colorSet;

    private Label timerNameLabel;
    private TextField timerNameTextField;
    private TimerChart chart;
    private Circle donutHole;
    private Label remainingMinuteLabel;
    private TextField maxMinuteTxtField;
    private VBox timerNode;

    public enum UpdateCheckResult {
        UPDATED, NO_CHANGE, REACHED_MAXIMUM;
    }

    public CountdownTimer(String timerName, int maxMinute, ColorSet colorSet) {
        this.timerName = timerName;
        this.maxSeconds = maxMinute * 60;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();
        this.colorSet = colorSet;

        timerNameLabel = new Label(timerName);
        timerNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    timerNameTextField.setText(CountdownTimer.this.timerName);
                    timerNameTextField.setVisible(true);
                }
            }
        });
        timerNameTextField = new TextField(timerName);
        timerNameTextField.setFont(new Font(20));
        timerNameTextField.setMaxSize(150, 10);
        timerNameTextField.setVisible(false);
        timerNameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                case ENTER:
                    String val = validateTimerName(timerNameTextField.getText());
                    if (val != null) {
                        changeTimerName(val);
                        timerNameTextField.setVisible(false);
                    } else {
                        timerNameTextField.setVisible(false);
                    }
                    break;
                case ESCAPE:
                    timerNameTextField.setVisible(false);
                    break;
                default:
                    break;
                }
            }
        });
        StackPane donutTop = new StackPane(timerNameLabel, timerNameTextField);

        donutHole = new Circle(100, Color.WHITESMOKE);
        donutHole.setStrokeWidth(0);
        chart = new TimerChart(maxMinute, 0, colorSet);
        donutHole.radiusProperty().bind(chart.heightProperty().multiply(0.35));
        remainingMinuteLabel = new Label(toRemainingText());
        remainingMinuteLabel.setTextFill(colorSet.remainingDimColor);
        remainingMinuteLabel.setFont(new Font(50));
        remainingMinuteLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() >= 2) {
                    maxMinuteTxtField.setText(toTextInMinute(maxSeconds));
                    maxMinuteTxtField.setVisible(true);
                }
            }
        });
        maxMinuteTxtField = new TextField(Integer.toString(maxMinute));
        maxMinuteTxtField.setFont(new Font(20));
        maxMinuteTxtField.setMaxSize(60, 10);
        maxMinuteTxtField.setVisible(false);
        maxMinuteTxtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch(event.getCode()) {
                case ENTER:
                    int val = validateMaxMinute(maxMinuteTxtField.getText());
                    if (val < 0) {
                        maxMinuteTxtField.setVisible(false);
                    } else {
                        changeMaxMinute(val);
                        maxMinuteTxtField.setVisible(false);
                    }
                    break;
                case ESCAPE:
                    maxMinuteTxtField.setVisible(false);
                    break;
                default:
                    break;
                }
            }
        });
        StackPane donutCenter = new StackPane(remainingMinuteLabel, maxMinuteTxtField);
        StackPane chartPane = new StackPane(chart, donutHole, donutCenter);
        chartPane.setMaxSize(300, 300);
        chartPane.setMinSize(300, 300);

        timerNode = new VBox(donutTop, chartPane);
        timerNode.setAlignment(Pos.TOP_CENTER);
    }

    private int validateMaxMinute(String input) {
        return Integer.parseInt(input);
    }

    private String validateTimerName(String input) {
        return input;
    }

    private void changeTimerName(String newName) {
        this.timerName = newName;
        timerNameLabel.setText(timerName);
    }

    private void changeMaxMinute(int newValue) {
        maxSeconds = newValue * 60;
        int passed = passedSeconds();
        int remaining = remainingSeconds(passed);
        updateChartAndRemainingLabel(remaining, passed);
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

    public void reset() {
        if (isActive) {
            throw new IllegalStateException("Pause the timer first.");
        }
        passedTimeInTotal = passedTimeInTotal.plus(passedTimeInRound);
        clearRoundVariable();
        int passed = passedSeconds();
        int remaining = remainingSeconds(passed);
        updateChartAndRemainingLabel(remaining, passed);
        System.out.println("reset" + timerNameLabel.textProperty().get());
    }

    public UpdateCheckResult checkAndUpdateIfNecessary() {
        int passed = passedSeconds();
        if (passed <= passedSeconds) {
            return UpdateCheckResult.NO_CHANGE;
        }
        passedSeconds = passed;
        if (passedSeconds > maxSeconds) {
            return UpdateCheckResult.REACHED_MAXIMUM;
        }
        int remaining = remainingSeconds(passedSeconds);
        updateChartAndRemainingLabel(remaining, passedSeconds);
        return UpdateCheckResult.UPDATED;
    }

    public boolean isActive() {
        return isActive;
    }

    private void updateChartAndRemainingLabel(int remaining, int passed) {
        chart.setTimeValues(remaining, passed);
        remainingMinuteLabel.setText(toTextInMinute(remaining));
    }

    private void clearRoundVariable() {
        passedSeconds = 0;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        startTime = null;
    }

    private int remainingSeconds() {
        return remainingSeconds(passedSeconds());
    }

    private int remainingSeconds(int passedSeconds) {
        return maxSeconds- passedSeconds;
    }

    private String toRemainingText() {
        return toTextInMinute(remainingSeconds());
    }

    private String toTextInMinute(int remainingSeconds) {
        return Integer.toString((int)Math.ceil(remainingSeconds / 60.0));
    }

    private int passedSeconds() {
        if(isActive) {
            Duration durationAfterStart = Duration.between(startTime, LocalDateTime.now());
            return (int)(durationAfterStart.getSeconds() + passedTimeInRound.getSeconds());
        } else {
            return (int)passedTimeInRound.getSeconds();
        }
    }

}
