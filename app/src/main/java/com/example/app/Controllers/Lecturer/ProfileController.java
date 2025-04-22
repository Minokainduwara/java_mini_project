package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    private Label userName;

    @FXML
    private Label address;

    @FXML
    private Label dob;

    @FXML
    private Label phone;

    @FXML
    private TextField updateUserName;

    @FXML
    private PasswordField password;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        System.out.println("ProfileController initialized.");

        updateUserName.setText(userName.getText());
    }

    @FXML
    private void handleProfileUpdate() {

        String updatedUsername = updateUserName.getText();
        String updatedPassword = password.getText();

        System.out.println("Updating profile...");
        System.out.println("New Username: " + updatedUsername);
        System.out.println("New Password: " + updatedPassword);


        userName.setText(updatedUsername);

        System.out.println("Profile updated successfully.");
    }

    @FXML
    private void handleCancel() {
        updateUserName.clear();
        password.clear();
        System.out.println("Text fields cleared.");
    }
}