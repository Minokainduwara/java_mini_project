package com.example.app.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.Objects;

public class AdminController {

    @FXML
    private Button logoutBtn;

    @FXML
    private Button dashboardbtn;

    @FXML
    private Button userBtn;

    @FXML
    private Button courseBtn;

    @FXML
    private Button timetableBtn;

    @FXML
    private AnchorPane paneCenter;

    @FXML
    private ImageView profileImg;

    @FXML
    public void initialize() {
        // Optional: If you don't want to modify FXML
        logoutBtn.setOnAction(this::logout);
        dashboardbtn.setOnAction(this::handleDashboard);
        userBtn.setOnAction(this::handleUser);
        courseBtn.setOnAction(this::handleCourse);
        timetableBtn.setOnAction(this::handleTimetable);
    }

    public void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Login.fxml")));
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Login");
            loginStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUI(String fileName) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Fxml/Admin/" + fileName)));
            paneCenter.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private void handleDashboard(ActionEvent event) {
        loadUI("dashboard.fxml");
    }


    private void handleUser(ActionEvent event) {
        loadUI("User.fxml");
    }


    private void handleCourse(ActionEvent event) {
        loadUI("Courses.fxml");
    }


    private void handleTimetable(ActionEvent event) {
        loadUI("Timetable.fxml");
    }



}