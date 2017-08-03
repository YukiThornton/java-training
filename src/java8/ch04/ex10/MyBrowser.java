package java8.ch04.ex10;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MyBrowser extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String homepage = "https://www.google.co.jp/";

        VBox vBox = new VBox(10);
        HBox hBox = new HBox(10);
        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();
        Button back = new Button("Back");
        back.setDisable(true);
        TextField urlBar = new TextField(homepage);
        urlBar.setPrefWidth(500);

        engine.load(homepage);
        back.setOnAction(event -> {
            WebHistory history = engine.getHistory();
            if (history.getCurrentIndex() > 0) {
                history.go(-1);
            }
        });
        engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue != Worker.State.SUCCEEDED) {
                    return;
                }
                if (engine.getHistory().getCurrentIndex() > 0) {
                    back.setDisable(false);
                } else {
                    back.setDisable(true);
                }
                System.out.println(engine.locationProperty().get());
                urlBar.textProperty().set(engine.locationProperty().get());
            }
        });
        urlBar.setOnAction(event -> {
            engine.load(urlBar.textProperty().get());
            vBox.requestFocus();
        });

        hBox.getChildren().addAll(back, urlBar);
        vBox.getChildren().addAll(hBox, browser);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.setTitle("My browser");
        primaryStage.show();
    }

}
