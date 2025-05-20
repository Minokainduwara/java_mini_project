package com.example.app.Controllers.TechnicalOfficer;

import com.example.app.Models.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.image.Image;



public class TechnicalOfficerController {
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadProfilePicture();
        loadUsername();
    }
    @FXML
    private AnchorPane paneCenter;

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/TechnicalOfficer/" + page + ".fxml"));
            Pane view = loader.load();

            if (page.equals("Profile")) {
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
    private ImageView profileImage;


    private void loadProfilePicture() {
        String query = "SELECT profile_picture FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("profile_picture");
                if (imageBytes != null && imageBytes.length > 0) {
                    InputStream is = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(is);
                    profileImage.setImage(image);

                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading profile image:");
            e.printStackTrace();
        }
    }

    @FXML
    private Label userName;
    private void loadUsername() {
        String query = "SELECT username FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                userName.setText(username);
            }
        } catch (SQLException e) {
            System.out.println("Error loading username:");
            e.printStackTrace();
            userName.setText("User");
        }
    }


    @FXML
    private void handleViewProfile(ActionEvent event) {
        System.out.println("Profile button pressed");
        loadPage("Profile");
    }


    @FXML
    private void handleAttendance(ActionEvent event) {
        System.out.println("Attendance button pressed");
        loadPage("Attendance");
    }

    @FXML
    private void handleMedical(ActionEvent event) {
        System.out.println("Medical button pressed");
        loadPage("Medical");
    }

    @FXML
    private void handleNotices(ActionEvent event) {
        System.out.println("Notices button pressed");
        loadPage("Notice");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
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
