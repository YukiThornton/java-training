package java8.ch04.ex06;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LayoutPractice extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button top = new Button("Top");
        HBox topBox = new HBox(top);
        topBox.setAlignment(Pos.CENTER);
        Button left = new Button("Left");
        Button center = new Button("Center");
        Button right = new Button("Right");
        Button bottom = new Button("Bottom");
        HBox bottomBox = new HBox(bottom);
        bottomBox.setAlignment(Pos.CENTER);
        BorderPane pane = new BorderPane(center, topBox, right, bottomBox, left);
        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

}
