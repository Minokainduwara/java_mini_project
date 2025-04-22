package com.example.app.Controllers.Lecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LectureController {
    @FXML
    private BorderPane mainPane;

    private void loadPage(String page) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/test_java_pro/" +page + ".fxml"));
        try {
            Pane view = loader.load();
            mainPane.setCenter(view);
        } catch (IOException e) {
            System.out.println("Failed to load: " + page);
            e.printStackTrace();
        }
    }

    @FXML
    private Label userName;

    @FXML
    private Button profileButton;

    @FXML
    private Button courseModuleButton, noticesButton, studentDetailsButton, logOutButton;


    @FXML
    private void initialize() {
        userName.setText("User Name: " + userName.getText());
    }


    @FXML
    private void profileButton(ActionEvent event) {
        loadPage("Profile");
        System.out.println("Profile button pressed");
    }

    @FXML
    private void homeButton(ActionEvent event) {

        System.out.println("Home button pressed");
    }

    @FXML
    private void courseModuleButton(ActionEvent event) {
        System.out.println("Course module button pressed");
        loadPage("CourseModule");
    }

    @FXML
    private void noticesButton(ActionEvent event) {
        System.out.println("Notices button pressed");
        loadPage("Notices");
    }

    @FXML
    private void studentDetailsButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage("StudentDetails");
    }

    @FXML
    private void lectureMaterialButton(ActionEvent event) {
        System.out.println("Lecture material button pressed");
        loadPage("LectureMaterial");
    }

    @FXML
    private void logOutButton(ActionEvent event) {

    }

}
