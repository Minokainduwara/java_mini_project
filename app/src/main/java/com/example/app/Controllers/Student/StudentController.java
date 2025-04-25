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
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/app/" + fxmlFile));
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
        loadScene("Views/Student/Home.fxml");
    }

    @FXML
    void handletimetable(ActionEvent event) {
        loadScene("Views/Student/Timetable.fxml");
    }

    @FXML
    void handlemedicle(ActionEvent event) {
        loadScene("Views/Student/Medical.fxml");
    }

    @FXML
    void handleexmresult(ActionEvent event) {
        loadScene("Views/Student/ExamResult.fxml");
    }

    @FXML
    void handleAttendece(ActionEvent event) {
        loadScene("Views/Student/Attendance.fxml");
    }

    @FXML
    void handleNotice(ActionEvent event) {
        loadScene("Views/Student/Notice.fxml");
    }

    // Subject label handlers (example: load subject-specific details)
    @FXML
    void handlesubject1(MouseEvent event) {
        System.out.println("Subject 1 clicked");
        // loadScene("Views/Student/Subject1.fxml");
    }

    @FXML
    void handleSubject2(MouseEvent event) {
        System.out.println("Subject 2 clicked");
        // loadScene("Views/Student/Subject2.fxml");
    }

    @FXML
    void handleSubject3(MouseEvent event) {
        System.out.println("Subject 3 clicked");
        // loadScene("Views/Student/Subject3.fxml");
    }

    @FXML
    void handleSubject$(MouseEvent event) {
        System.out.println("Subject 4 clicked");
        // loadScene("Views/Student/Subject4.fxml");
    }

    @FXML
    void handleSubject5(MouseEvent event) {
        System.out.println("Subject 5 clicked");
        // loadScene("Views/Student/Subject5.fxml");
    }
}
