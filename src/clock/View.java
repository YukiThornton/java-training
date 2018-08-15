package clock;

import static clock.ViewHelper.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.List;

import static clock.ColorPalette.BG_COLORS;
import static clock.ColorPalette.BG_COLOR_KEY;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class View {

    static class Builder {
      private final AppState state;
      private final Stage appStage;
      private LocalDateTime timeToDisplayOnClock;
      private Runnable onClosedAction;
      private Runnable deleteModeSwitchAction;
      private Runnable reportAction;
      private Runnable addWorkTimerAction;
      private Runnable addBreakTimerAction;
      private Runnable startTimerAction;
      private Runnable pauseTimerAction;
      private Runnable skipNextTimerAction;
      private Runnable stopPomoAction;
      private Predicate<String> validatorForTimerName;
      private Predicate<String> validatorForCountdownTime;
      private Consumer<String> onInvalidInputForTimerName;
      private Consumer<String> onInvalidInputForTimerDuration;

      Builder(AppState state, Stage appStage) {
          this.state = state;
          this.appStage = appStage;
      }

      Builder registerClock(LocalDateTime dateTime) {
          this.timeToDisplayOnClock = dateTime;
          return this;
      }

      Builder onClosed(Runnable onClosed) {
          this.onClosedAction = onClosed;
          return this;
      }

      Builder registerDeleteModeAction(Runnable action) {
          this.deleteModeSwitchAction = action;
          return this;
      }

      Builder registerReportAction(Runnable action) {
          this.reportAction = action;
          return this;
      }

      Builder registerAddWorkTimerAction(Runnable action) {
          this.addWorkTimerAction = action;
          return this;
      }

      Builder registerAddBreakTimerAction(Runnable action) {
          this.addBreakTimerAction = action;
          return this;
      }

      Builder registerStartTimerAction(Runnable action) {
          this.startTimerAction = action;
          return this;
      }

      Builder registerPauseTimerAction(Runnable action) {
          this.pauseTimerAction = action;
          return this;
      }

      Builder registerSkipNextTimerAction(Runnable action) {
          this.skipNextTimerAction = action;
          return this;
      }

      Builder registerStopPomoAction(Runnable action) {
          this.stopPomoAction = action;
          return this;
      }

      Builder setValidatorForTimerName(Predicate<String> validator) {
          this.validatorForTimerName = validator;
          return this;
      }

      Builder setValidatorForCountdownTime(Predicate<String> validator) {
          this.validatorForCountdownTime = validator;
          return this;
      }

      Builder onInvalidInputForTimerName(Consumer<String> action) {
          this.onInvalidInputForTimerName = action;
          return this;
      }

      Builder onInvalidInputForTimerDuration(Consumer<String> action) {
          this.onInvalidInputForTimerDuration = action;
          return this;
      }



      View build() {
          if (canBuild()) {
              return new View(this);
          } else {
              throw new IllegalStateException("Some required actions are missing.");
          }
      }

      private boolean canBuild() {
          return (timeToDisplayOnClock != null)
                 && (onClosedAction != null)
                 && (deleteModeSwitchAction != null)
                 && (reportAction != null)
                 && (addWorkTimerAction != null)
                 && (addBreakTimerAction != null)
                 && (startTimerAction != null)
                 && (pauseTimerAction != null)
                 && (skipNextTimerAction != null)
                 && (stopPomoAction != null)
                 && (validatorForTimerName != null)
                 && (validatorForCountdownTime != null)
                 && (onInvalidInputForTimerName != null)
                 && (onInvalidInputForTimerDuration != null)
                 ;
      }
    }

    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 340;
    private static final double WINDOW_MIN_HEIGHT = 535;
    private static final String APP_TITLE = "Pomopomo Timer";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final ColorPalette.Key TEXT_COLOR_KEY = ColorPalette.Key.DARK;

    private final Stage appStage;
    private Label clockDateLabel;
    private Label clockTimeLabel;
    private List<TimerCard> timerCards = new ArrayList<>();
    private int selectedTimerCardIndex;
    private ControlButton deleteModeSwitch;
    private ControlButton reportButton;
    private ControlButton addWorkTimerButton;
    private ControlButton addBreakTimerButton;
    private ControlButton startTimerButton;
    private ControlButton pauseTimerButton;
    private ControlButton skipNextTimerButton;
    private ControlButton stopPomoButton;
    private final Set<ControlButton> colorChangeableButtons = new HashSet<>();

    private View(Builder builder) {
        BorderPane rootPane = createEmptyPane();
        appStage = setupStage(builder.appStage, rootPane, builder);

        createItems(builder);
        setupRootPaneWithItems(rootPane);
        selectedTimerCardIndex = builder.state.currentTimerIndex();
    }

    private Stage setupStage(Stage stage, BorderPane rootPane, Builder builder) {
        Scene scene = createScene(rootPane);
        setBackgroundColor(scene, rootPane);

        stage.setOnCloseRequest(e -> builder.onClosedAction.run());
        stage.setScene(scene);
        stage.setTitle(APP_TITLE);
        stage.setHeight(WINDOW_PREF_HEIGHT);
        stage.setWidth(WINDOW_PREF_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        return stage;
    }

    private void setBackgroundColor(Scene scene, BorderPane pane) {
        scene.getStylesheets().add("clock/css/main.css");
        pane.setStyle("-fx-background-color: " + BG_COLORS.getTextOf(BG_COLOR_KEY) + ";");
    }

    private void createItems(Builder builder) {
        addClockDateTimeLabels(builder.timeToDisplayOnClock, builder.state);
        for(Timer timer : builder.state.timers()) {
            TimerCard card = new TimerCardImpl.Builder(timer)
                                .setValidatorForTimerName(builder.validatorForTimerName)
                                .setValidatorForCountdownTime(builder.validatorForCountdownTime)
                                .onInvalidInputForTimerName(builder.onInvalidInputForTimerName)
                                .onInvalidInputForTimerDuration(builder.onInvalidInputForTimerDuration)
                                .onTimerDeletionRequested((c,s) -> System.out.println("hello"))
                                .build();
            timerCards.add(card);
        }

        deleteModeSwitch = setupButton(IconButton.DELETE_MODE_SWITCH, builder.deleteModeSwitchAction);
        reportButton = setupButton(IconButton.REPORT, builder.reportAction);
        addWorkTimerButton = setupButton(IconButton.ADD_WORK_TIMER, builder.addWorkTimerAction);
        addBreakTimerButton = setupButton(IconButton.ADD_BREAK_TIMER, builder.addBreakTimerAction);
        startTimerButton = setupButton(IconButton.START, builder.startTimerAction);
        pauseTimerButton = setupButton(IconButton.PAUSE, builder.pauseTimerAction);
        skipNextTimerButton = setupButton(IconButton.SKIP, builder.skipNextTimerAction);
        stopPomoButton = setupButton(IconButton.STOP, builder.stopPomoAction);
    }

    private ControlButton setupButton(ControlButton button, Runnable action) {
        button.setOnMouseClicked(e -> action.run());
        if (button.canChangeColorPalette()) {
            colorChangeableButtons.add(button);
        }
        return button;
    }

    private void setupRootPaneWithItems(BorderPane rootPane) {
        rootPane.setPadding(new Insets(10, 20, 10, 10));

        rootPane.setTop(createHeader());
        rootPane.setCenter(createBody());
//        rootBox.setCenter(createVBox(Pos.CENTER, timeLabel, timerScrlPane, ctrlBtns));
        rootPane.setBottom(createFooter());
//        rootBox.widthProperty().addListener((observable, oldValue, newValue) -> {
//            double btnWidth = addBreakTimerBtn.getWidth() + skipBtn.getWidth() + stopBtn.getWidth() + (addWorkTimerBtn.getWidth() + addBreakTimerBtn.getWidth()) * 2;
//            if (btnWidth >= rootBox.getWidth()) {
//                hideNode(timerBtns, false);
//            } else {
//                hideNode(timerBtns, true);
//            }
//        });
//        rootBox.heightProperty().addListener((observable, oldValue, newValue) -> {
//            if (rootBox.getHeight() <= WINDOW_MIN_HEIGHT - 10) {
//                hideNode(topBox, false);
//                hideNode(bottomBox, false);
//            } else {
//                hideNode(topBox, true);
//                hideNode(bottomBox, true);
//            }
//        });
    }

    private Node createHeader() {
        BorderPane headerPane = createEmptyPane();
        deleteModeSwitch.get().setPadding(new Insets(0, 15, 0, 0));
        HBox headerRight = 
                createHBox(
                        Pos.TOP_RIGHT,
                        deleteModeSwitch.get(),
                        reportButton.get()
                );
        headerPane.setRight(headerRight);
        headerPane.setLeft(clockDateLabel);
        return headerPane;
    }

    private Node createBody() {
        Region[] timerCardRegions = timerCards.stream().map(timerCard -> timerCard.get()).toArray(Region[]::new);
        HBox timerCardBox = createHBox(Pos.CENTER, timerCardRegions);
        HBox ctrlBtns =
                createHBox(
                        Pos.CENTER,
                        stopPomoButton.get(),
                        startTimerButton.get(),
                        pauseTimerButton.get(),
                        skipNextTimerButton.get()
                );
        ctrlBtns.setSpacing(30);
        return createVBox(Pos.CENTER, clockTimeLabel, timerCardBox, ctrlBtns);
    }

    private Node createFooter() {
        HBox timerBtns = createHBox(Pos.CENTER_RIGHT, addWorkTimerButton.get(), addBreakTimerButton.get());
        return createVBox(null, timerBtns);
    }

    void show() {
        appStage.show();
    }

    void updateClock(LocalDateTime dateTime) {
        clockDateLabel.setText(formatDate(dateTime));
        clockTimeLabel.setText(formatTime(dateTime));
    }

    void updateTimerTime(Timer.Values values) {
        timerCards.get(selectedTimerCardIndex).updateTime(values);
    }

    void selectTimer(int index) {
        selectedTimerCardIndex = index;
    }

    void activateRunningView() {
        startTimerButton.hide();
        pauseTimerButton.show();
        stopPomoButton.hide();
    }

    void deactivateRunningView() {
        startTimerButton.show();
        pauseTimerButton.hide();
        stopPomoButton.show();
    }

    void activateTimerDeletionView() {
        timerCards.stream().forEach(card -> card.showDeleteTimerButton());
    }

    void deactivateTimerDeletionView() {
        timerCards.stream().forEach(card -> card.hideDeleteTimerButton());
    }

    void showInfoDialog(DialogMessage message) {
        showAlertAndWait(message.title(), message.content(), AlertType.INFORMATION, false);
    }

    void showInfoDialogOnTop(DialogMessage message) {
        showAlertAndWait(message.title(), message.content(), AlertType.INFORMATION, true);
    }

    private void addClockDateTimeLabels(LocalDateTime timeToDisplayOnClock, AppState state) {
        Color textColor = getTextColor(state);
        this.clockDateLabel = createTextLabel(formatDate(timeToDisplayOnClock), AppFont.TEXT_20.get(), textColor);
        this.clockTimeLabel = createTextLabel(formatTime(timeToDisplayOnClock), AppFont.TEXT_30.get(), textColor);
    }

    private static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(DATE_FORMATTER);
    }

    private static String formatTime(LocalDateTime dateTime) {
        return dateTime.format(TIME_FORMATTER);
    }

    private static Color getTextColor(AppState state) {
        return state.currentColorPalette().get(TEXT_COLOR_KEY);
    }
}
