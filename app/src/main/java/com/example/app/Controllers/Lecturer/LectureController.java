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
        System.out.println("Profile button pressed");
        loadPage("/FXML/Lecturer/Profile");
    }

    @FXML
    private void handleHomeButton(ActionEvent event) {
        System.out.println("Home button pressed");
    }

    @FXML
    private void handleLectureMaterialButton(ActionEvent event) {
        System.out.println("Lecture material button pressed");
        loadPage("/FXML/Lecturer/LectureMaterial");
    }

    @FXML
    private void handleCourseModuleButton(ActionEvent event) {
        System.out.println("Course module button pressed");
        loadPage("/FXML/Lecturer/CourseModule");
    }

    @FXML
    private void handleNoticesButton(ActionEvent event) {
        System.out.println("Notices button pressed");
        loadPage(("/FXML/Lecturer/Notices"));
    }

    @FXML
    private void handleStudentDetailsButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage(("/FXML/Lecturer/StudentDetails"));
    }

    @FXML
    private void handleLogOutButton(ActionEvent event) {
        System.out.println("Logout button pressed");
    }

}
