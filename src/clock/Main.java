package clock;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(1);
        LocalDateTime clockTime = LocalDateTime.now();
        String message = clockTime.toString();
        Label label = new Label();
        label.setText(message);
        label.setFont(new Font(100));
        box.getChildren().add(label);

        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle(message);
        primaryStage.show();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            
            @Override
            public void run() {
                Platform.runLater(() -> {
                    label.setText(LocalDateTime.now().toString());
                });
            }
        }, 0, 500);
        primaryStage.setOnCloseRequest(evet -> {
            timer.cancel();
        });
    }

}
