package com.example.app.Controllers.Lecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LectureController {
    @FXML
    private AnchorPane paneCenter;

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Lecturer/" + page + ".fxml"));
            Pane view = loader.load();
            paneCenter.getChildren().setAll(view);
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
    private void handleViewProfile(ActionEvent event) {
        loadPage("Profile");
        System.out.println("Profile button pressed");
    }


    @FXML
    private void handleCourseModuleButton(ActionEvent event) {
        System.out.println("Course module button pressed");
        loadPage("CourseModule");
    }

    @FXML
    private void handleNoticesButton(ActionEvent event) {
        System.out.println("Notices button pressed");
        loadPage("Notices");
    }

    @FXML
    private void handleStudentDetailsButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage("StudentDetails");
    }

    @FXML
    private void handleLectureMaterialButton(ActionEvent event) {
        System.out.println("Lecture material button pressed");
        loadPage("LectureMaterial");
    }

    //@FXML
    //private void logOutButton(ActionEvent event) {

    //}

}
