package com.example.app.Controllers.Lecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LectureController {
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }
    @FXML
    private AnchorPane paneCenter;

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Lecturer/" + page + ".fxml"));
            Pane view = loader.load();

            if (page.equals("LectureMaterial")) {
                LectureMaterialController controller = loader.getController();
                controller.setUserId(userId);
            } else if (page.equals("CourseModule")) {
                CourseModuleController controller = loader.getController();
                controller.setUserId(userId); // Pass userId for CourseModule
            } else if (page.equals("Profile")) {
                ProfileController controller = loader.getController();
                controller.setUserId(userId);
            }

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
    private void handleStudentMarksButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage("StudentMarks");
    }

    @FXML
    private void handleLectureMaterialButton(ActionEvent event) {
        System.out.println("Lecture material button pressed");
        loadPage("LectureMaterial");
    }

    @FXML
    private void logOutButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
