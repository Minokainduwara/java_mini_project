package com.example.app.Controllers.Lecturer;

import com.example.app.Models.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class ProfileController {

    @FXML
    private Label userName;

    @FXML
    private Label email;

    @FXML
    private Label department;

    @FXML
    private Label roleLabel;

    @FXML
    private Label phoneNumber;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private ComboBox<String> departmentInput;

    @FXML
    private Button updateProfilePicture;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView profileImage;

    private int userId;


    public void initialize(int userId) {
        this.userId = userId;
        departmentInput.getItems().addAll(
                "ICT",
                "ET",
                "BST",
                "MDS"
        );
        loadUserProfile();
    }


    private void loadUserProfile() {
        String query = "SELECT * FROM User WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                userName.setText(rs.getString("name"));
                email.setText(rs.getString("email"));
                department.setText(rs.getString("department"));
                phoneNumber.setText(rs.getString("phone"));
                roleLabel.setText(rs.getString("role"));

                // Set profile picture
                byte[] imageData = rs.getBytes("profile_picture");
                if (imageData != null) {
                    Image image = new Image(new java.io.ByteArrayInputStream(imageData));
                    profileImage.setImage(image);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleProfileUpdate() {
        String name = nameInput.getText().trim();
        String email = emailInput.getText().trim();
        String phone = phoneNumberInput.getText().trim();
        String department = departmentInput.getValue();

        String updateQuery = "UPDATE User SET name = ?, email = ?, phone = ?, department = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateQuery)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, department);
            stmt.setInt(5, userId);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile updated successfully.", ButtonType.OK);
                alert.showAndWait();
                loadUserProfile(); // Reload the updated profile data
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error updating profile: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(updateProfilePicture.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);

            try (Connection connection = DatabaseConnection.getConnection()) {
                String updatePicQuery = "UPDATE User SET profile_picture = ? WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(updatePicQuery)) {
                    FileInputStream fis = new FileInputStream(selectedFile);
                    stmt.setBinaryStream(1, fis, (int) selectedFile.length());
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profile picture updated successfully.", ButtonType.OK);
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Error uploading profile picture.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void handleCancel() {
        nameInput.clear();
        emailInput.clear();
        phoneNumberInput.clear();
        departmentInput.setValue(null);
    }

    // Set the user ID for profile view
    public void setUserId(int userId) {
        this.userId = userId;
        loadUserProfile();
    }
}
