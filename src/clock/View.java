package clock;

import static clock.ViewHelper.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class View {

    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 340;
    private static final double WINDOW_MIN_HEIGHT = 535;
    private static final String APP_TITLE = "Pomopomo Timer";
    private static final String INFO_REPORT_TITLE = "Good job!";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
    private static final ColorPalette.Key TEXT_COLOR_KEY = ColorPalette.Key.DARK;

    static class Builder {
        private final AppState state;
        private final View view;
        private final Scene scene;
        private final BorderPane rootPane = createEmptyPane();

        Builder(AppState state, Stage appStage) {
            this.state = state;
            scene = createScene(rootPane);

            setBackgroundColor(scene, rootPane);
            setupStage(appStage, scene);

            this.view = new View(appStage);
        }

        Builder addClock(LocalDateTime dateTime) {
            Color textColor = getTextColor(state);
            view.addClockDateTimeLabels(
                createTextLabel(formatDate(dateTime), AppFont.TEXT_20.get(), textColor),
                createTextLabel(formatTime(dateTime), AppFont.TEXT_30.get(), textColor)
            );
            return this;
          }

        Builder addDeleteModeSwitch(Runnable onToggled) {
            IconButton deleteBtn = IconButton.DELETE;
            deleteBtn.setOnMouseClicked(e -> onToggled.run());

            view.addDeleteModeSwitch(deleteBtn);
            return this;
        }

        Builder addReportButton(Runnable onPressed) {
            IconButton reportBtn = IconButton.REPORT;
            reportBtn.setOnMouseClicked(e -> onPressed.run());

            view.addReportButton(reportBtn);
            return this;
        }

        Builder onCloseBtnClicked(Runnable action) {
            view.appStage.setOnCloseRequest(e -> action.run());
            return this;
        }

        View build() {
            setupRootPaneWithItems();
            return view;
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
//            rootBox.setCenter(createVBox(Pos.CENTER, timeLabel, timerScrlPane, ctrlBtns));
//            rootBox.setBottom(bottomBox);
//            rootBox.widthProperty().addListener((observable, oldValue, newValue) -> {
//                double btnWidth = addBreakTimerBtn.getWidth() + skipBtn.getWidth() + stopBtn.getWidth() + (addWorkTimerBtn.getWidth() + addBreakTimerBtn.getWidth()) * 2;
//                if (btnWidth >= rootBox.getWidth()) {
//                    hideNode(timerBtns, false);
//                } else {
//                    hideNode(timerBtns, true);
//                }
//            });
//            rootBox.heightProperty().addListener((observable, oldValue, newValue) -> {
//                if (rootBox.getHeight() <= WINDOW_MIN_HEIGHT - 10) {
//                    hideNode(topBox, false);
//                    hideNode(bottomBox, false);
//                } else {
//                    hideNode(topBox, true);
//                    hideNode(bottomBox, true);
//                }
//            });
        }
 
        private Node createHeader() {
            BorderPane headerPane = createEmptyPane();
            if (view.deleteModeSwitch != null || view.reportButton != null) {
                // TODO: Add null check
                view.deleteModeSwitch.get().setPadding(new Insets(0, 15, 0, 0));
                HBox headerRight = 
                        createHBox(
                                Pos.TOP_RIGHT,
                                view.deleteModeSwitch != null ? view.deleteModeSwitch.get(): null,
                                view.reportButton != null ? view.reportButton.get(): null
                        );
                headerPane.setRight(headerRight);
            }
            if (view.clockDateLabel != null) {
                headerPane.setLeft(view.clockDateLabel);
            }
            return headerPane;
        }

        private Node createBody() {
            if (view.clockTimeLabel != null) {
                return createVBox(Pos.CENTER, view.clockTimeLabel);
            }
            return createVBox(Pos.CENTER);
        }
    }

    private final Stage appStage;
    private Label clockDateLabel;
    private Label clockTimeLabel;
    private IconButton deleteModeSwitch;
    private IconButton reportButton;

    private View(Stage appStage) {
        this.appStage = appStage;
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

    private void addClockDateTimeLabels(Label date, Label time) {
        this.clockDateLabel = date;
        this.clockTimeLabel = time;
    }

    private void addDeleteModeSwitch(IconButton deleteModeSwitch) {
        this.deleteModeSwitch = deleteModeSwitch;
    }

    private void addReportButton(IconButton reportButton) {
        this.reportButton = reportButton;
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
