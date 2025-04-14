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
        FXMLLoader loader = new FXMLLoader(getClass().getResource(page + ".fxml"));
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
    private void handleProfileButton(ActionEvent event) {
        loadPage("/FXML/Lecturer/Profile");
    }

    @FXML
    private void handleHomeButton(ActionEvent event) {

    }

    @FXML
    private void handleCourseModuleButton(ActionEvent event) {
        loadPage("/FXML/Lecturer/CourseModule");
    }

    @FXML
    private void handleNoticesButton(ActionEvent event) {
        loadPage(("/FXML/Lecturer/Notices"));
    }

    @FXML
    private void handleStudentDetailsButton(ActionEvent event) {
        loadPage(("/FXML/Lecturer/StudentDetails"));
    }

    @FXML
    private void handleLogOutButton(ActionEvent event) {

    }

}
