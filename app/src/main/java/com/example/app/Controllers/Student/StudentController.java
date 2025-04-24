package com.example.app.Controllers.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;

import java.io.IOException;

public class StudentController {

    @FXML
    private AnchorPane paneCenter; // Make sure this ID matches the pane in your main scene where content is loaded

    // Load FXML pages into the center pane
    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Student/" + page + ".fxml"));
            Pane view = loader.load();
            paneCenter.getChildren().setAll(view);
        } catch (IOException e) {
            System.out.println("Failed to load: " + page);
            e.printStackTrace();
        }
    }

    // Home Button Action
    @FXML
    private void handlegotohome(ActionEvent event) {
        loadPage("home");
    }

    // Time Table Button Action
    @FXML
    private void handletimetable(ActionEvent event) {
        loadPage("timetable");
    }

    // Medical Report Button Action
    @FXML
    private void handlemedicle(ActionEvent event) {
        loadPage("medicle");
        System.out.println("Medical Report button pressed");
    }

    // Exam Result Button Action
    @FXML
    private void handleexmresult(ActionEvent event) {
        loadPage("exam_result");
        System.out.println("Exam Result button pressed");
    }

    // Attendance View Button Action
    @FXML
    private void handleAttendece(ActionEvent event) {
        loadPage("attendance");
        System.out.println("Attendance View button pressed");
    }

    // Notice Button Action
    @FXML
    private void handleNotice(ActionEvent event) {
        loadPage("notice");
        System.out.println("Notice button pressed");
}
}
