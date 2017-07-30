package java8.ch04.ex04;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CircleViewer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Circle");
        Circle circle = new Circle();
        Group group = new Group(circle);
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        circle.centerXProperty().bind(Bindings.divide(scene.widthProperty(), 2));
        circle.centerYProperty().bind(Bindings.divide(scene.heightProperty(), 2));
        circle.radiusProperty().bind(Bindings.divide(scene.heightProperty(), 2));

        primaryStage.show();
    }

}
