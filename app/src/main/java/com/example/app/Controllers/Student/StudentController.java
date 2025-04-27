package com.example.app.Controllers.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentController {

    @FXML
    private Button attendancebtn;

    @FXML
    private Button examresultbtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button medicalbtn;

    @FXML
    private Button noticebtn;

    @FXML
    private Button timetableBtn;

    @FXML
    private Label subject1btn;

    @FXML
    private Label subject2;

    @FXML
    private Label subject3btn;

    @FXML
    private Label subject4btn;

    @FXML
    private Label subject5btn;

    // Utility method to change scenes
    private void loadScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Fxml/Student/" + fxmlFile));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Navigation methods
    @FXML
    void handlegotohome(ActionEvent event) {
        loadScene("Home");
    }

    @FXML
    void handletimetable(ActionEvent event) {
        loadScene("timetable");
    }

    @FXML
    void handlemedicle(ActionEvent event) {
        loadScene("medical");
    }

    @FXML
    void handleexmresult(ActionEvent event) {
        loadScene("examResult");
    }

    @FXML
    void handleAttendece(ActionEvent event) {
        loadScene("Attendance");
    }

    @FXML
    void handleNotice(ActionEvent event) {
        loadScene("notice");
    }


    @FXML
    void handlesubject1(MouseEvent event) {
        System.out.println("Subject 1 clicked");
    }

    @FXML
    void handleSubject2(MouseEvent event) {
        System.out.println("Subject 2 clicked");
    }

    @FXML
    void handleSubject3(MouseEvent event) {
        System.out.println("Subject 3 clicked");
    }

    @FXML
    void handleSubject4(MouseEvent event) {
        System.out.println("Subject 4 clicked");

    }

    @FXML
    void handleSubject5(MouseEvent event) {
        System.out.println("Subject 5 clicked");

    }
}
