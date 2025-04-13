package com.example.app;
<<<<<<< HEAD
=======

>>>>>>> 03ec36a8ab635b12ca50bf9ebee31c0ce714067e
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Correctly loading the FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("TECLMS");
        primaryStage.setScene(scene);
        primaryStage.show();
>>>>>>> 03ec36a8ab635b12ca50bf9ebee31c0ce714067e
    }
}
    public static void main(String[] args) {
      launch(args);
    }