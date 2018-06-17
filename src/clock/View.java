package clock;

import static clock.ViewHelper.*;
import static clock.ColorPalette.BG_COLORS;
import static clock.ColorPalette.BG_COLOR_KEY;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

class View {

    private static final double WINDOW_PREF_WIDTH = 800;
    private static final double WINDOW_PREF_HEIGHT = 700;
    private static final double WINDOW_MIN_WIDTH = 340;
    private static final double WINDOW_MIN_HEIGHT = 535;
    private static final String APP_TITLE = "Pomopomo Timer";

    static class Builder {
        private final AppState state;
        private final View view;
        private final BorderPane rootPane;
        private final Scene scene;

        Builder(AppState state, Stage appStage) {
            this.state = state;
            rootPane = createEmptyPane();
            scene = createScene(rootPane);
            setBackgroundColor(scene, rootPane);
            setupStage(appStage, scene);
            this.view = new View(appStage);
        }

        Builder addDeleteModeSwitch(Runnable onToggled) {
          IconButton deleteBtn = IconButton.DELETE;
          deleteBtn.setOnMouseClicked(e -> onToggled.run());
          view.addDeleteModeSwitch(deleteBtn);
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

        private BorderPane createEmptyPane() {
            return new BorderPane();
        }

        private Scene createScene(BorderPane pane) {
            return new Scene(pane);
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
            BorderPane topBox = new BorderPane();
            topBox.setRight(createHBox(Pos.TOP_RIGHT, view.deleteModeSwitch.get()));
            rootPane.setTop(topBox);
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
    }

    private final Stage appStage;
    private IconButton deleteModeSwitch;

    private View(Stage appStage) {
        this.appStage = appStage;
    }

    void show() {
        appStage.show();
    }

    private void addDeleteModeSwitch(IconButton deleteModeSwitch) {
        this.deleteModeSwitch = deleteModeSwitch;
    }
}
