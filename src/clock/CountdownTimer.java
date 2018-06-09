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

//    public enum TimerPurpose {
//        WORK("work", "work", 25), REST("rest", "rest", 5);
//
//        private String initialTimerName;
//        private String verb;
//        private int initialTimerMinute;
//
//        private TimerPurpose(String initialTimerName, String verb, int initialTimerMinute) {
//            this.initialTimerName = initialTimerName;
//            this.verb = verb;
//            this.initialTimerMinute = initialTimerMinute;
//        }
//
//        public String initialTimerName() {
//            return initialTimerName;
//        }
//
//        public String verb() {
//            return verb;
//        }
//
//        public int initialTimerMinute() {
//            return initialTimerMinute;
//        }
//    }
//
//    public enum TimerType {
//        
//        WORK_BLUE(ColorPalette.BLUE, TimerPurpose.WORK),
//        REST_YELLOW(ColorPalette.YELLOW, TimerPurpose.REST);
//
//        private ColorPalette colorPalette;
//        private TimerPurpose purpose;
//
//        private TimerType(ColorPalette colorGroup, TimerPurpose purpose) {
//            this.colorPalette = colorGroup;
//            this.purpose = purpose;
//        }
//
//        public ColorPalette getColorPalette() {
//            return colorPalette;
//        }
//
//        public TimerPurpose getPurpose() {
//            return purpose;
//        }
//
//        public String initialTimerName() {
//            return purpose.initialTimerName();
//        }
//
//        public int initialTimerMinute() {
//            return purpose.initialTimerMinute();
//        }
//    }
//
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
        

        chart = new TimerChart(timerType.initialTimerMinute(), 0, timerType.colorPalette());
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

        Label label = createLabelBtn(initialText, AppFont.TEXT_30);
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

        TextField textField = createTextField(initialText, AppFont.TEXT_20, 150, 10, false);
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

        Label deleteBtn = createLabelBtn(BTN_TXT_DELETE, AppFont.ICON_30);
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

        Label label = createLabelBtn(toRemainingText(), AppFont.TEXT_50);
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

        TextField textField = createTextField(Integer.toString(maxMin), AppFont.TEXT_20, 80, 10, false);
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
        ColorPalette colorPalette = timerType.colorPalette();
        label.setTextFill(colorPalette.get(ColorPalette.Key.LIGHT));
        label.setOnMouseEntered((event) -> {
            if (!isActive()) {
                label.setTextFill(colorPalette.get(ColorPalette.Key.DARK));
            }
        });
        label.setOnMouseExited((event) -> {
            if (!isActive()) {
                label.setTextFill(colorPalette.get(ColorPalette.Key.LIGHT));
            }
        });
    }

    Node getNode() {
        return rootNode;
    }

    void setVisibleOnDeleteBtn(boolean visibleAndManaged) {
        if (isActive()) {
            return;
        }
        deleteBtn.setVisible(visibleAndManaged);
    }

    TimerType getTimerType() {
        return timerType;
    }

    ColorPalette getColorPalette() {
        return timerType.colorPalette();
    }

    void select() {
        setFixedSize(centerBox, CENTERBOX_SIZE_BIG);
    }

    void deselect() {
        setFixedSize(centerBox, CENTERBOX_SIZE_SMALL);
    }

    void start() {
        startTime = LocalDateTime.now();
        remainingMinuteLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.SATURATED_DARK));
        chart.brighterColor();
        timerNameLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.SATURATED_DARK));
        isActive = true;
    }

    void pause() {
        remainingMinuteLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.LIGHT));
        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
        chart.dimColor();
        timerNameLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.LIGHT));
        isActive = false;
    }

    void reset() {
        if (isActive) {
            throw new IllegalStateException("Pause the timer first.");
        }
        passedTimeInTotal = passedTimeInTotal.plus(passedTimeInRound);
        clearRoundVariable();
        int passed = passedSeconds();
        int remaining = remainingSeconds(passed);
        updateChartAndRemainingLabel(remaining, passed);
    }

    void clearHistory() {
        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
        clearRoundVariable();
    }

    TimerReport getReport() {
        if (isActive()) {
            return new TimerReport(passedTimeInTotal.plus(passedTimeInRound).plus(Duration.between(startTime, LocalDateTime.now())), timerName);
        } else {
            return new TimerReport(passedTimeInTotal.plus(passedTimeInRound), timerName);
        }
    }
    
    UpdateCheckResult checkAndUpdateIfNecessary() {
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

    boolean isActive() {
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
