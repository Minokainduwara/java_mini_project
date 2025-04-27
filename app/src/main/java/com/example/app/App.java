package com.example.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch(args); // Correctly launching the JavaFX application
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Correctly loading the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("TECLMS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}