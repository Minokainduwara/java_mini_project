package com.example.app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;



import static com.example.app.Util.DatabaseInitializer.initializeDatabase;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("University Management System - Login");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw the exception to ensure proper shutdown
        }

    }

    public static void main(String[] args) {
        if (initializeDatabase()) {
            System.out.println("Database initialized successfully!😊");
        } else {
            System.err.println("Failed to initialize database. Check logs for details.");
        }
        launch(args);
    }
}