package clock;

import static clock.NodeTools.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CountdownTimer {
    private static final String BTN_TXT_DELETE = "\uf00d";
    private static final int MAX_VALID_MINUTE = 999;
    private static final int MAX_TIMER_NAME_SIZE = 15;
    private static final double CENTERBOX_SIZE_SMALL = 230;
    private static final double CENTERBOX_SIZE_BIG = 300;

    private Duration passedTimeInRound;
    private Duration passedTimeInTotal;
    private LocalDateTime startTime;
    private int passedSeconds;
    private int maxSeconds;
    private String timerName;
    private boolean initialized = false;
    private boolean isActive = false;
    private boolean exceeded = false;
    private TimerType timerType;
    private Consumer<CountdownTimer> onTimerDeleteBtnSelectedAction;
    private Consumer<CountdownTimer> onInvalidInputForMaxMinuteAction;
    private Consumer<CountdownTimer> onInvalidInputForTimerNameAction;

    private Label timerNameLabel;
    private TextField timerNameInput;
    private TimerChart chart;
    private Circle donutHole;
    private Label remainingMinuteLabel;
    private TextField maxMinuteInput;
    private Node deleteBtn;
    private Region centerBox;
    private Node rootNode;

    public enum UpdateCheckResult {
        UPDATED, NO_CHANGE, HIT_MAXIMUM, OVER_MAXIMUM;
    }

    public enum TimerPurpose {
        WORK("work", "work", 25), REST("rest", "rest", 5);

        private String initialTimerName;
        private String verb;
        private int initialTimerMinute;

        private TimerPurpose(String initialTimerName, String verb, int initialTimerMinute) {
            this.initialTimerName = initialTimerName;
            this.verb = verb;
            this.initialTimerMinute = initialTimerMinute;
        }

        public String initialTimerName() {
            return initialTimerName;
        }

        public String verb() {
            return verb;
        }

        public int initialTimerMinute() {
            return initialTimerMinute;
        }
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

        public String initialTimerName() {
            return purpose.initialTimerName();
        }

        public int initialTimerMinute() {
            return purpose.initialTimerMinute();
        }
    }

    public CountdownTimer(TimerType timerType, Color bgColor) {
        this.timerName = timerType.initialTimerName();
        this.maxSeconds = timerType.initialTimerMinute() * 60;
        this.timerType = timerType;
        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        startTime = LocalDateTime.now();

        timerNameLabel = createTimerNameLabel(timerName);
        timerNameInput = createTimerNameInput(timerName);
        Node timerNameNode = new StackPane(timerNameLabel, timerNameInput);
        deleteBtn = createDeleteBtn();
        Node hiddenDeleteBtn = createDeleteBtn();
        hiddenDeleteBtn.setVisible(false);
        BorderPane topBox = new BorderPane();
        topBox.setLeft(hiddenDeleteBtn);
        topBox.setCenter(timerNameNode);
        topBox.setRight(deleteBtn);
        

        chart = new TimerChart(timerType.initialTimerMinute(), 0, timerType.getColorSet());
        donutHole = createHole(chart, bgColor);
        remainingMinuteLabel = createRemainingMinuteLabel();
        maxMinuteInput = createMaxMinuteInput(timerType.initialTimerMinute());
        Node donutCenter = new StackPane(remainingMinuteLabel, maxMinuteInput);
        centerBox = createDonut(chart, donutHole, donutCenter);
        Region centerArea = createHBox(Pos.CENTER, centerBox);
        setFixedSize(centerArea, CENTERBOX_SIZE_BIG);

        rootNode = createRootNode(topBox, centerArea);
        initialized = true;
    }

    public void onTimerDeleteBtnSelected(Consumer<CountdownTimer> consumer) {
        this.onTimerDeleteBtnSelectedAction = consumer;
    }

    public void onInvalidInputForMaxMinute(Consumer<CountdownTimer> consumer) {
        this.onInvalidInputForMaxMinuteAction = consumer;
    }

    public void onInvalidInputForTimerName(Consumer<CountdownTimer> consumer) {
        this.onInvalidInputForTimerNameAction = consumer;
    }

    private Label createTimerNameLabel(String initialText) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label label = createTextBtn(initialText, FONT_SMALL);
        label.setOnMouseClicked((event) -> {
            if (!isActive()) {
                if (timerNameInput == null) {
                    throw new IllegalStateException("The input is not initialized yet.");
                }
                showHiddenTextField(timerNameInput, CountdownTimer.this.timerName);
            }
        });
        setColorOnLabel(label);
        return label;
    }

    private TextField createTimerNameInput(String initialText) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        TextField textField = createTextField(initialText, FONT_TINY, 150, 10, false);
        setInvisibleOnFocusLost(textField);
        acceptOnEnterAndSetInvisibleOnEscape(textField, text -> {
            if (validateTimerName(text)) {
                changeTimerName(text);
                textField.setVisible(false);
            } else {
                if (onInvalidInputForTimerNameAction != null) {
                    onInvalidInputForTimerNameAction.accept(this);
                    textField.setVisible(true);
                }
            }
        });
        return textField;
    }

    private Region createDonut(Node donutRing, Node donutHole, Node donutCenter) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        StackPane chartPane = new StackPane(donutRing, donutHole, donutCenter);
        setFixedSize(chartPane, CENTERBOX_SIZE_SMALL);
        return chartPane;
    }

    private Circle createHole(TimerChart donutRing, Color bgColor) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Circle circle = new Circle(100, bgColor);
        circle.setStrokeWidth(0);
        circle.radiusProperty().bind(donutRing.heightProperty().multiply(0.35));
        return circle;
    }

    private Node createDeleteBtn() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label deleteBtn = createIconBtn(BTN_TXT_DELETE, IconFont.SMALL);
        deleteBtn.setOnMouseClicked((event) -> {
            if (onTimerDeleteBtnSelectedAction == null) {
                throw new IllegalStateException("No action is set.");
            }
            onTimerDeleteBtnSelectedAction.accept(CountdownTimer.this);
        });
        setColorOnLabel(deleteBtn);
        deleteBtn.setVisible(false);
        return deleteBtn;
    }

    private Label createRemainingMinuteLabel() {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        Label label = createTextBtn(toRemainingText(), FONT_MEDIUM);
        label.setOnMouseClicked((event) -> {
            if (!isActive()) {
                if (maxMinuteInput == null) {
                    throw new IllegalStateException("The input is not initialized yet.");
                }
                showHiddenTextField(maxMinuteInput, toTextInMinute(maxSeconds));
            }
        });
        setColorOnLabel(label);
        return label;
    }

    private TextField createMaxMinuteInput(int maxMin) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        TextField textField = createTextField(Integer.toString(maxMin), FONT_TINY, 80, 10, false);
        setInvisibleOnFocusLost(textField);
        acceptOnEnterAndSetInvisibleOnEscape(textField, text -> {
            if (validateMaxMinute(text)) {
                changeMaxMinute(Integer.parseInt(text));
                textField.setVisible(false);
            } else {
                if (onInvalidInputForMaxMinuteAction != null) {
                    onInvalidInputForMaxMinuteAction.accept(this);
                    textField.setVisible(true);
                }
            }
        });
        return textField;
    }

    private Node createRootNode(Node... contents) {
        if (initialized) {
            throw new IllegalStateException("Already initialized.");
        }

        VBox box = new VBox(contents);
        box.setAlignment(Pos.TOP_CENTER);
        return box;
    }

    private boolean validateMaxMinute(String input) {
        try {
            int val = Integer.parseInt(input);
            if (val <= MAX_VALID_MINUTE && val > 0) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateTimerName(String input) {
        return input.length() <= MAX_TIMER_NAME_SIZE;
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

    private void setColorOnLabel(Label label) {
        ColorSet colorSet = timerType.colorSet;
        label.setTextFill(colorSet.lightColor());
        label.setOnMouseEntered((event) -> {
            if (!isActive()) {
                label.setTextFill(colorSet.darkColor());
            }
        });
        label.setOnMouseExited((event) -> {
            if (!isActive()) {
                label.setTextFill(colorSet.lightColor());
            }
        });
    }

    public Node getNode() {
        return rootNode;
    }

    public void setVisibleOnDeleteBtn(boolean visibleAndManaged) {
        if (isActive()) {
            return;
        }
        deleteBtn.setVisible(visibleAndManaged);
    }

    public TimerType getTimerType() {
        return timerType;
    }

    public TimerPurpose getTimerPurpose() {
        return timerType.getPurpose();
    }

    public ColorSet getColorSet() {
        return timerType.getColorSet();
    }

    public void select() {
        setFixedSize(centerBox, CENTERBOX_SIZE_BIG);
    }

    public void deselect() {
        setFixedSize(centerBox, CENTERBOX_SIZE_SMALL);
    }

    public void start() {
        startTime = LocalDateTime.now();
        remainingMinuteLabel.setTextFill(timerType.getColorSet().saturatedDarkColor());
        chart.brighterColor();
        timerNameLabel.setTextFill(timerType.colorSet.saturatedDarkColor());
        isActive = true;
    }

    public void pause() {
        remainingMinuteLabel.setTextFill(timerType.getColorSet().lightColor());
        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
        chart.dimColor();
        timerNameLabel.setTextFill(timerType.colorSet.lightColor());
        isActive = false;
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
    }

    public void clearHistory() {
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        clearRoundVariable();
    }

    public TimerReport getReport() {
        if (isActive()) {
            return new TimerReport(passedTimeInTotal.plus(passedTimeInRound).plus(Duration.between(startTime, LocalDateTime.now())), timerName);
        } else {
            return new TimerReport(passedTimeInTotal.plus(passedTimeInRound), timerName);
        }
    }
    
    public UpdateCheckResult checkAndUpdateIfNecessary() {
        int passed = passedSeconds();
        if (passed <= passedSeconds) {
            return UpdateCheckResult.NO_CHANGE;
        }
        passedSeconds = passed;
        if (passedSeconds > maxSeconds && !exceeded) {
            exceeded = true;
            return UpdateCheckResult.HIT_MAXIMUM;
        }
        int remaining = remainingSeconds(passedSeconds);
        updateChartAndRemainingLabel(remaining, passedSeconds);
        if (exceeded) {
            return UpdateCheckResult.OVER_MAXIMUM;
        }
        return UpdateCheckResult.UPDATED;
    }

    public boolean isActive() {
        return isActive;
    }

    private void updateChartAndRemainingLabel(int remaining, int passed) {
        if (remaining >= 0) {
            chart.setTimeValues(remaining, passed);
        }
        remainingMinuteLabel.setText(toTextInMinute(remaining));
    }

    private void clearRoundVariable() {
        exceeded = false;
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
