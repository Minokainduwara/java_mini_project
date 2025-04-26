package com.example.app.Controllers.Admin;

import com.example.app.Models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
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
    private ImageView imgField;

    private final UserModel userModel = new UserModel();

    @FXML
    public void initialize() {
        logoutBtn.setOnAction(this::logout);
        dashboardbtn.setOnAction(this::handleDashboard);
        userBtn.setOnAction(this::handleUser);
        courseBtn.setOnAction(this::handleCourse);
        timetableBtn.setOnAction(this::handleTimetable);

        loadProfileImage("admin");
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

    private void loadProfileImage(String username) {
        InputStream is = userModel.getProfilePictureByUsername(username);
        if (is != null) {
            try {
                Image image = new Image(is);
                imgField.setImage(image);

                double radius = Math.min(imgField.getFitWidth(), imgField.getFitHeight()) / 2;
                Circle circleClip = new Circle(radius, radius, radius);
                imgField.setClip(circleClip);

                System.out.println("Profile picture loaded successfully.");
            } catch (Exception e) {
                System.out.println("Error loading profile picture: " + e.getMessage());
            }
        } else {
            System.out.println("No profile image found for user: " + username);
            Image defaultImg = new Image(Objects.requireNonNull(getClass().getResource("/Images/Admin/sample2.jpg")).toExternalForm());
            imgField.setImage(defaultImg);

            double radius = Math.min(imgField.getFitWidth(), imgField.getFitHeight()) / 2;
            Circle circleClip = new Circle(radius, radius, radius);
            imgField.setClip(circleClip);
        }
    }

    private void handleDashboard(ActionEvent event) {
        loadUI("Notices.fxml");
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
