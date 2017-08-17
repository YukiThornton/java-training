package clock;

import static clock.NodeTools.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CountdownTimer {
    private static final String BTN_TXT_DELETE = "\uE872";

    private Duration passedTimeInRound;
    private Duration passedTimeInTotal;
    private LocalDateTime startTime;
    private int passedSeconds;
    private int maxSeconds;
    private String timerName;
    private boolean isActive = false;
    private TimerType timerType;

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

    public enum TimerPurpose {
        WORK, REST;
    }

    public enum TimerType {
        
        WORK_BLUE(ColorSet.BLUE, TimerPurpose.WORK),
        REST_YELLOW(ColorSet.YELLOW, TimerPurpose.REST);

        private ColorSet colorSet;
        private TimerPurpose purpose;
        private TimerType(ColorSet colorSet, TimerPurpose purpose) {
            this.colorSet = colorSet;
            this.purpose = purpose;
        }
        public ColorSet getColorSet() {
            return colorSet;
        }
        public TimerPurpose getPurpose() {
            return purpose;
        }
    }

    public CountdownTimer(String timerName, int maxMinute, TimerType timerType, Consumer<CountdownTimer> timerDeleteAction) {
        this.timerName = timerName;
        this.maxSeconds = maxMinute * 60;
        this.timerType = timerType;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();

        StackPane donutTop = initDonutTop(timerName);
        StackPane donut = initDonut(maxMinute);
        StackPane donutBottom = initDonutBottom(timerDeleteAction);

        timerNode = new VBox(donutTop, donut, donutBottom);
        timerNode.setAlignment(Pos.TOP_CENTER);
    }

    public ColorSet getColorSet() {
        return timerType.getColorSet();
    }

    private StackPane initDonutTop(String donutTitle) {
        timerNameLabel = createTextBtn(donutTitle, FONT_SMALL, timerType.colorSet.remainingDimColor);
        timerNameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isActive() && event.getClickCount() >= 2) {
                    showHiddenTextField(timerNameTextField, CountdownTimer.this.timerName);
                }
            }
        });
        timerNameTextField = createTextField(donutTitle, FONT_TINY, 150, 10, false);
        setInvisibleOnFocusLost(timerNameTextField);
        acceptOnEnterAndSetInvisibleOnEscape(timerNameTextField, text -> {
            String val = validateTimerName(text);
            if (val != null) {
                changeTimerName(val);
                timerNameTextField.setVisible(false);
            } else {
                timerNameTextField.setVisible(false);
            }
        });
        return new StackPane(timerNameLabel, timerNameTextField);
    }

    private StackPane initDonut(int maxMin) {
        chart = new TimerChart(maxMin, 0, timerType.getColorSet());
        donutHole = new Circle(100, Color.WHITESMOKE);
        donutHole.setStrokeWidth(0);
        donutHole.radiusProperty().bind(chart.heightProperty().multiply(0.35));
        StackPane donutCenter = initDonutCenter(maxMin);
        StackPane chartPane = new StackPane(chart, donutHole, donutCenter);
        chartPane.setMaxSize(300, 300);
        chartPane.setMinSize(300, 300);
        return chartPane;
    }

    private StackPane initDonutBottom(Consumer<CountdownTimer> timerDeleteAction) {
        Label deleteBtn = createIconBtn(BTN_TXT_DELETE, FONT_SMALL, timerType.colorSet.remainingDimColor);
        deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                timerDeleteAction.accept(CountdownTimer.this);
            }
        });
        return new StackPane(deleteBtn);
    }

    private StackPane initDonutCenter(int maxMin) {
        remainingMinuteLabel = createTextBtn(toRemainingText(), FONT_MEDIUM, timerType.colorSet.remainingDimColor);
        remainingMinuteLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isActive() && event.getClickCount() >= 2) {
                    showHiddenTextField(maxMinuteTxtField, toTextInMinute(maxSeconds));
                }
            }
        });
        maxMinuteTxtField = createTextField(Integer.toString(maxMin), FONT_TINY, 60, 10, false);
        setInvisibleOnFocusLost(maxMinuteTxtField);
        acceptOnEnterAndSetInvisibleOnEscape(maxMinuteTxtField, text -> {
            int val = validateMaxMinute(text);
            if (val < 0) {
                maxMinuteTxtField.setVisible(false);
            } else {
                changeMaxMinute(val);
                maxMinuteTxtField.setVisible(false);
            }
        });
        return new StackPane(remainingMinuteLabel, maxMinuteTxtField);
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
        remainingMinuteLabel.setTextFill(timerType.getColorSet().remainingColor);
        chart.brighterColor();
        timerNameLabel.setTextFill(timerType.colorSet.remainingColor);
        isActive = true;
        System.out.println("start" + timerNameLabel.textProperty().get());
    }

    public void pause() {
        remainingMinuteLabel.setTextFill(timerType.getColorSet().remainingDimColor);
        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
        chart.dimColor();
        timerNameLabel.setTextFill(timerType.colorSet.remainingDimColor);
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
