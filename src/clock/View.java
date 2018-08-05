package clock;

import static clock.ViewHelper.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

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
                 && (reportAction != null);
      }
    }

    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 340;
    private static final double WINDOW_MIN_HEIGHT = 535;
    private static final String APP_TITLE = "Pomopomo Timer";
    private static final String INFO_REPORT_TITLE = "Good job!";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final ColorPalette.Key TEXT_COLOR_KEY = ColorPalette.Key.DARK;

    private final Stage appStage;
    private final Scene scene;
    private final BorderPane rootPane = createEmptyPane();
    private Label clockDateLabel;
    private Label clockTimeLabel;
    private final ControlButton deleteModeSwitch;
    private final ControlButton reportButton;
    private final Set<ControlButton> colorChangeableButtons;

    private View(Builder builder) {
        appStage = builder.appStage;
        scene = createScene(rootPane);
        setBackgroundColor(scene, rootPane);
        setupStage(appStage, scene);
        appStage.setOnCloseRequest(e -> builder.onClosedAction.run());

        addClockDateTimeLabels(builder.timeToDisplayOnClock, builder.state);

        deleteModeSwitch = createDeleteModeSwitch(builder.deleteModeSwitchAction);
        reportButton = createReportButton(builder.reportAction);

        colorChangeableButtons = new HashSet<>();

        setupRootPaneWithItems();

    }

    private void setBackgroundColor(Scene scene, BorderPane pane) {
        scene.getStylesheets().add("clock/css/main.css");
        pane.setStyle("-fx-background-color: " + BG_COLORS.getTextOf(BG_COLOR_KEY) + ";");
    }

    private void setupStage(Stage stage, Scene scene) {
        stage.setScene(scene);
        stage.setTitle(APP_TITLE);
        stage.setHeight(WINDOW_PREF_HEIGHT);
        stage.setWidth(WINDOW_PREF_WIDTH);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
    }

    private void setupRootPaneWithItems() {
        rootPane.setPadding(new Insets(10, 20, 10, 10));

        rootPane.setTop(createHeader());
        rootPane.setCenter(createBody());
//        rootBox.setCenter(createVBox(Pos.CENTER, timeLabel, timerScrlPane, ctrlBtns));
//        rootBox.setBottom(bottomBox);
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
        return createVBox(Pos.CENTER, clockTimeLabel);
    }

    void show() {
        appStage.show();
    }

    void updateClock(LocalDateTime dateTime) {
        clockDateLabel.setText(formatDate(dateTime));
        clockTimeLabel.setText(formatTime(dateTime));
    }

    void showReport(String content) {
//        String reportStr = "";
//        for (int i = 0; i < reports.size(); i++) {
//            reportStr += reports.get(i).toString() + "\n";
//        }
        showAlertAndWait(INFO_REPORT_TITLE, content, AlertType.INFORMATION, true);
    }

    private void addClockDateTimeLabels(LocalDateTime timeToDisplayOnClock, AppState state) {
        Color textColor = getTextColor(state);
        this.clockDateLabel = createTextLabel(formatDate(timeToDisplayOnClock), AppFont.TEXT_20.get(), textColor);
        this.clockTimeLabel = createTextLabel(formatTime(timeToDisplayOnClock), AppFont.TEXT_30.get(), textColor);
    }

    private ControlButton createDeleteModeSwitch(Runnable deleteModeSwitchAction) {
        ControlButton btn = IconButton.DELETE;
        btn.setOnMouseClicked(e -> deleteModeSwitchAction.run());
        return btn;
    }

    private ControlButton createReportButton(Runnable reportAction) {
        ControlButton btn = IconButton.REPORT;
        btn.setOnMouseClicked(e -> reportAction.run());
        return btn;
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
