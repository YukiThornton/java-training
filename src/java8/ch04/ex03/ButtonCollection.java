package java8.ch04.ex03;

import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonCollection extends Application {

    private StringProperty selectedBtnTxtProperty = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox box = new VBox();
        TextField textField = new TextField("gggg");
        box.getChildren().add(textField);
        Arrays.asList("button1", "button2", "button3", "button4", "button5").forEach((text) -> {
            Button btn = new Button(text);
            btn.setOnAction(event -> {
                if (selectedBtnTxtProperty != null) {
                    selectedBtnTxtProperty.unbind();
                }
                selectedBtnTxtProperty = btn.textProperty();
                textField.textProperty().set(selectedBtnTxtProperty.get());
                selectedBtnTxtProperty.bind(textField.textProperty());
            });
            box.getChildren().add(btn);
        });
        primaryStage.setScene(new Scene(box));
        primaryStage.setTitle("Buttons!!!");
        primaryStage.show();
    }
}
