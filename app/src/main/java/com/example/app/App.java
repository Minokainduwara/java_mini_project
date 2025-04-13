package com.example.app;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class App extends Application{
    double x,y;
    @Override
    public void start(Stage primarystage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getResource("Views/Login.fxml"));
       primarystagestage.setTitle("Student Dashboard");
    }
}
    public static void main(String[] args) {
      launch(args);
    }