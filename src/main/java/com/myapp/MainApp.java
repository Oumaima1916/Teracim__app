package com.myapp; // ولا اللي عندك انت

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass().getResource("/main_view.fxml")
        );

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("My JavaFX App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
