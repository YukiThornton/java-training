package java8.ch04.ex01;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HelloFX extends Application{

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox(2);
        String message = "Hello, FX";
        Label label = new Label();
        label.setText(message);
        label.setFont(new Font(100));
        box.getChildren().add(label);

        TextField textField = new TextField();
        textField.setText(message);
        textField.setFont(new Font(100));
        label.textProperty().bind(textField.textProperty());
        box.getChildren().add(textField);

        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle(message);
        primaryStage.show();
    }

}
