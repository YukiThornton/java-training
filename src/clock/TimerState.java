package clock;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimerState {


    private Duration elapsedDurationInInterval;
    private Duration elapsedDurationInTotal;
    private LocalDateTime startedTime;

    private final TimerType timerType;
    private int intervalInSecond;
    private String timerName;

//  private int passedSeconds;
//  private boolean isActive = false;
//private boolean initialized = false;
//  private boolean exceeded = false;

//    enum UpdateCheckResult {
//        UPDATED, NO_CHANGE, HIT_MAXIMUM, OVER_MAXIMUM;
//    }
//
    TimerState(TimerType timerType) {
        this.timerType = timerType;
        this.timerName = timerType.initialTimerName();
        this.intervalInSecond = timerType.initialTimerMinute() * 60;
        this.startedTime = null;
        this.elapsedDurationInInterval = Duration.of(0, ChronoUnit.SECONDS);
        this.elapsedDurationInTotal = Duration.of(0, ChronoUnit.SECONDS);
    }

    boolean isRunning() {
        return startedTime != null;
    }

    TimerType timerType() {
        return timerType;
    }

    ColorPalette colorPalette() {
        return timerType.colorPalette();
    }

    String timerName() {
        return timerName;
    }

    int intervalInSecond() {
        return intervalInSecond;
    }

    LocalDateTime startedTime() {
        return startedTime;
    }

    Duration elapsedDurationInInterval() {
        return elapsedDurationInInterval;
    }

    Duration elapsedDurationInTotal() {
        return elapsedDurationInTotal;
    }

    void setTimerName(String newName) {
        this.timerName = newName;
    }

    void setIntervalLength(int second) {
        this.intervalInSecond = second;
    }

    void setStartedTime(LocalDateTime time) {
        this.startedTime = time;
    }

    void setElapsedDurationInInterval(Duration duration) {
        this.elapsedDurationInInterval = duration;
    }

    void setElapsedDurationInTotal(Duration duration) {
        this.elapsedDurationInTotal = duration;
    }

//    void changeIntervalLength(int minute) {
//        intervalInSecond = minute * 60;
//    }
//
//    void select() {
//        setFixedSize(centerBox, CENTERBOX_SIZE_BIG);
//    }
//
//    void deselect() {
//        setFixedSize(centerBox, CENTERBOX_SIZE_SMALL);
//    }

//    void start() {
//        startTime = LocalDateTime.now();
//        remainingMinuteLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.SATURATED_DARK));
//        chart.brighterColor();
//        timerNameLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.SATURATED_DARK));
//        isActive = true;
//    }

//    void pause() {
//        remainingMinuteLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.LIGHT));
//        passedTimeInRound = passedTimeInRound.plus(Duration.between(startTime, LocalDateTime.now()));
//        chart.dimColor();
//        timerNameLabel.setTextFill(timerType.colorPalette().get(ColorPalette.Key.LIGHT));
//        isActive = false;
//    }
//
//    void reset() {
//        if (isActive) {
//            throw new IllegalStateException("Pause the timer first.");
//        }
//        passedTimeInTotal = passedTimeInTotal.plus(passedTimeInRound);
//        clearRoundVariable();
//        int passed = passedSeconds();
//        int remaining = remainingSeconds(passed);
//        updateChartAndRemainingLabel(remaining, passed);
//    }
//
//    void clearHistory() {
//        elapsedDurationInTotal = Duration.of(0, ChronoUnit.SECONDS);
//        clearRoundVariable();
//    }

//    TimerReport getReport() {
//        if (isActive()) {
//            return new TimerReport(elapsedDurationInTotal.plus(elapsedDurationInInterval).plus(Duration.between(startedTime, LocalDateTime.now())), timerName);
//        } else {
//            return new TimerReport(elapsedDurationInTotal.plus(elapsedDurationInInterval), timerName);
//        }
//    }
//    
//    UpdateCheckResult checkAndUpdateIfNecessary() {
//        int passed = passedSeconds();
//        if (passed <= passedSeconds) {
//            return UpdateCheckResult.NO_CHANGE;
//        }
//        passedSeconds = passed;
//        if (passedSeconds > maxSeconds && !exceeded) {
//            exceeded = true;
//            return UpdateCheckResult.HIT_MAXIMUM;
//        }
//        int remaining = remainingSeconds(passedSeconds);
//        updateChartAndRemainingLabel(remaining, passedSeconds);
//        if (exceeded) {
//            return UpdateCheckResult.OVER_MAXIMUM;
//        }
//        return UpdateCheckResult.UPDATED;
//    }
//
//    boolean isActive() {
//        return isActive;
//    }

//    private void clearRoundVariable() {
//        exceeded = false;
//        passedSeconds = 0;
//        elapsedDurationInInterval = Duration.of(0, ChronoUnit.SECONDS);
//        startedTime = null;
//    }

//    private int remainingSeconds() {
//        return remainingSeconds(passedSeconds());
//    }
//
//    private int remainingSeconds(int passedSeconds) {
//        return intervalInSecond- passedSeconds;
//    }
//
//    private String toRemainingText() {
//        return toTextInMinute(remainingSeconds());
//    }
//
//    private String toTextInMinute(int remainingSeconds) {
//        return Integer.toString((int)Math.ceil(remainingSeconds / 60.0));
//    }
//
//    private int passedSeconds() {
//        if(startedTime != null) {
//            Duration durationAfterStart = Duration.between(startedTime, LocalDateTime.now());
//            return (int)(durationAfterStart.getSeconds() + elapsedDurationInInterval.getSeconds());
//        } else {
//            return (int)elapsedDurationInInterval.getSeconds();
//        }
//    }





    // controller
//  private static final int MAX_VALID_MINUTE = 999;
//  private static final int MAX_TIMER_NAME_SIZE = 15;
//  private Consumer<TimerState> onTimerDeleteBtnSelectedAction;
//  private Consumer<TimerState> onInvalidInputForMaxMinuteAction;
//  private Consumer<TimerState> onInvalidInputForTimerNameAction;

//  public void onTimerDeleteBtnSelected(Consumer<TimerState> consumer) {
//  this.onTimerDeleteBtnSelectedAction = consumer;
//}
//
//public void onInvalidInputForMaxMinute(Consumer<TimerState> consumer) {
//  this.onInvalidInputForMaxMinuteAction = consumer;
//}
//
//public void onInvalidInputForTimerName(Consumer<TimerState> consumer) {
//  this.onInvalidInputForTimerNameAction = consumer;
//}
//
//    private boolean validateMaxMinute(String input) {
//        try {
//            int val = Integer.parseInt(input);
//            if (val <= MAX_VALID_MINUTE && val > 0) {
//                return true;
//            }
//            return false;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    private boolean validateTimerName(String input) {
//        return input.length() <= MAX_TIMER_NAME_SIZE;
//    }




    // view
//  private static final String BTN_TXT_DELETE = "\uf00d";
//  private static final double CENTERBOX_SIZE_SMALL = 230;
//  private static final double CENTERBOX_SIZE_BIG = 300;

//  private Label timerNameLabel;
//  private TextField timerNameInput;
//  private TimerChart chart;
//  private Circle donutHole;
//  private Label remainingMinuteLabel;
//  private TextField maxMinuteInput;
//  private Node deleteBtn;
//  private Region centerBox;
//  private Node rootNode;

//    TimerState(TimerType timerType, Color bgColor) {
//        this.timerName = timerType.initialTimerName();
//        this.maxSeconds = timerType.initialTimerMinute() * 60;
//        this.timerType = timerType;
//        passedTimeInRound = Duration.of(0, ChronoUnit.SECONDS);
//        passedTimeInTotal = Duration.of(0, ChronoUnit.SECONDS);
//        startTime = LocalDateTime.now();
//
//        timerNameLabel = createTimerNameLabel(timerName);
//        timerNameInput = createTimerNameInput(timerName);
//        Node timerNameNode = new StackPane(timerNameLabel, timerNameInput);
//        deleteBtn = createDeleteBtn();
//        Node hiddenDeleteBtn = createDeleteBtn();
//        hiddenDeleteBtn.setVisible(false);
//        BorderPane topBox = new BorderPane();
//        topBox.setLeft(hiddenDeleteBtn);
//        topBox.setCenter(timerNameNode);
//        topBox.setRight(deleteBtn);
//        
//
//        chart = new TimerChart(timerType.initialTimerMinute(), 0, timerType.colorPalette());
//        donutHole = createHole(chart, bgColor);
//        remainingMinuteLabel = createRemainingMinuteLabel();
//        maxMinuteInput = createMaxMinuteInput(timerType.initialTimerMinute());
//        Node donutCenter = new StackPane(remainingMinuteLabel, maxMinuteInput);
//        centerBox = createDonut(chart, donutHole, donutCenter);
//        Region centerArea = createHBox(Pos.CENTER, centerBox);
//        setFixedSize(centerArea, CENTERBOX_SIZE_BIG);
//
//        rootNode = createRootNode(topBox, centerArea);
//        initialized = true;
//    }
//  private void setColorOnLabel(Label label) {
//  ColorPalette colorPalette = timerType.colorPalette();
//  label.setTextFill(colorPalette.get(ColorPalette.Key.LIGHT));
//  label.setOnMouseEntered((event) -> {
//      if (!isActive()) {
//          label.setTextFill(colorPalette.get(ColorPalette.Key.DARK));
//      }
//  });
//  label.setOnMouseExited((event) -> {
//      if (!isActive()) {
//          label.setTextFill(colorPalette.get(ColorPalette.Key.LIGHT));
//      }
//  });
//}
//    void select() {
//        setFixedSize(centerBox, CENTERBOX_SIZE_BIG);
//    }
//
//    void deselect() {
//        setFixedSize(centerBox, CENTERBOX_SIZE_SMALL);
//    }
//
//    private void updateChartAndRemainingLabel(int remaining, int passed) {
//        if (remaining >= 0) {
//            chart.setTimeValues(remaining, passed);
//        }
//        remainingMinuteLabel.setText(toTextInMinute(remaining));
//    }

//Node getNode() {
//  return rootNode;
//}
//
//    private void changeTimerName(String newName) {
//        this.timerName = newName;
//        timerNameLabel.setText(timerName);
//    }
//
//    private void changeMaxMinute(int newValue) {
//        maxSeconds = newValue * 60;
//        int passed = passedSeconds();
//        int remaining = remainingSeconds(passed);
//        updateChartAndRemainingLabel(remaining, passed);
//    }

//void setVisibleOnDeleteBtn(boolean visibleAndManaged) {
//  if (isActive()) {
//      return;
//  }
//  deleteBtn.setVisible(visibleAndManaged);
//}
//  private Label createTimerNameLabel(String initialText) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  Label label = createLabelBtn(initialText, AppFont.TEXT_30);
//  label.setOnMouseClicked((event) -> {
//      if (!isActive()) {
//          if (timerNameInput == null) {
//              throw new IllegalStateException("The input is not initialized yet.");
//          }
//          showHiddenTextField(timerNameInput, TimerState.this.timerName);
//      }
//  });
//  setColorOnLabel(label);
//  return label;
//}
//
//private TextField createTimerNameInput(String initialText) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  TextField textField = createTextField(initialText, AppFont.TEXT_20, 150, 10, false);
//  setInvisibleOnFocusLost(textField);
//  acceptOnEnterAndSetInvisibleOnEscape(textField, text -> {
//      if (validateTimerName(text)) {
//          changeTimerName(text);
//          textField.setVisible(false);
//      } else {
//          if (onInvalidInputForTimerNameAction != null) {
//              onInvalidInputForTimerNameAction.accept(this);
//              textField.setVisible(true);
//          }
//      }
//  });
//  return textField;
//}
//
//private Region createDonut(Node donutRing, Node donutHole, Node donutCenter) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  StackPane chartPane = new StackPane(donutRing, donutHole, donutCenter);
//  setFixedSize(chartPane, CENTERBOX_SIZE_SMALL);
//  return chartPane;
//}
//
//private Circle createHole(TimerChart donutRing, Color bgColor) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  Circle circle = new Circle(100, bgColor);
//  circle.setStrokeWidth(0);
//  circle.radiusProperty().bind(donutRing.heightProperty().multiply(0.35));
//  return circle;
//}
//
//private Node createDeleteBtn() {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  Label deleteBtn = createLabelBtn(BTN_TXT_DELETE, AppFont.ICON_30);
//  deleteBtn.setOnMouseClicked((event) -> {
//      if (onTimerDeleteBtnSelectedAction == null) {
//          throw new IllegalStateException("No action is set.");
//      }
//      onTimerDeleteBtnSelectedAction.accept(TimerState.this);
//  });
//  setColorOnLabel(deleteBtn);
//  deleteBtn.setVisible(false);
//  return deleteBtn;
//}
//
//private Label createRemainingMinuteLabel() {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  Label label = createLabelBtn(toRemainingText(), AppFont.TEXT_50);
//  label.setOnMouseClicked((event) -> {
//      if (!isActive()) {
//          if (maxMinuteInput == null) {
//              throw new IllegalStateException("The input is not initialized yet.");
//          }
//          showHiddenTextField(maxMinuteInput, toTextInMinute(maxSeconds));
//      }
//  });
//  setColorOnLabel(label);
//  return label;
//}
//
//private TextField createMaxMinuteInput(int maxMin) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  TextField textField = createTextField(Integer.toString(maxMin), AppFont.TEXT_20, 80, 10, false);
//  setInvisibleOnFocusLost(textField);
//  acceptOnEnterAndSetInvisibleOnEscape(textField, text -> {
//      if (validateMaxMinute(text)) {
//          changeMaxMinute(Integer.parseInt(text));
//          textField.setVisible(false);
//      } else {
//          if (onInvalidInputForMaxMinuteAction != null) {
//              onInvalidInputForMaxMinuteAction.accept(this);
//              textField.setVisible(true);
//          }
//      }
//  });
//  return textField;
//}
//
//private Node createRootNode(Node... contents) {
//  if (initialized) {
//      throw new IllegalStateException("Already initialized.");
//  }
//
//  VBox box = new VBox(contents);
//  box.setAlignment(Pos.TOP_CENTER);
//  return box;
//}


}
