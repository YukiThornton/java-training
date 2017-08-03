package java8.ch04.ex09;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlanetAnimation extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Star and planet");
        Circle sun = new Circle(20);
        Circle planet = new Circle(10);
        Pane group = new Pane();
        Scene scene = new Scene(group);
        primaryStage.setScene(scene);

        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        sun.setFill(Color.DARKRED);
        sun.centerXProperty().bind(Bindings.divide(scene.widthProperty(), 2));
        sun.centerYProperty().bind(Bindings.divide(scene.heightProperty(), 2));
        planet.setFill(Color.CORNFLOWERBLUE);
        group.getChildren().addAll(sun, planet);
        primaryStage.show();
        Ellipse ellipse = new Ellipse();
        ellipse.centerXProperty().bind(sun.centerXProperty());
        ellipse.centerYProperty().bind(sun.centerYProperty());
        ellipse.translateXProperty().bind(sun.translateXProperty());
        ellipse.translateYProperty().bind(sun.translateYProperty());
        ellipse.setRadiusX(300);
        ellipse.setRadiusY(200);
        PathTransition transition = new PathTransition(Duration.millis(3000), ellipse, planet);
        transition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
    }
}
