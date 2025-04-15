package com.example.app.Controllers;

import com.example.app.Models.User;
import com.example.app.Services.LoginService;
import com.example.app.Util.FormUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private CheckBox rememberMeCheckbox;
    
    private final LoginService loginService = new LoginService();

    @FXML
    public void initialize() {
        // Initialize any components or load saved preferences
        // For example, you could load a saved username if "Remember me" was checked
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        // Get user input
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }

        // Authenticate user with our new LoginService
        User user = loginService.login(username, password);

        if (user != null) {
            // Authentication successful
            // Save preferences if "Remember me" is checked
            if (rememberMeCheckbox.isSelected()) {
                saveUserPreferences(username);
            }

            // Navigate to appropriate dashboard based on user role
            navigateToDashboard(user);
        } else {
            showError("Invalid username or password");
        }
    }

    @FXML
    public void handleForgotPassword(ActionEvent event) {
        // TODO: Implement forgot password logic
        System.out.println("Forgot password clicked");
    }

    private void saveUserPreferences(String username) {
        // TODO: Save username to preferences
        System.out.println("Saving preferences for: " + username);
    }

    private void navigateToDashboard(User user) {
        try {
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            String fxmlPath;
            
            // Determine which dashboard to load based on user role
            switch (user.getRole()) {
                case "Admin":
                    fxmlPath = "Admin/Admin.fxml";
                    break;
                case "Lecturer":
                    fxmlPath = "Lecturer/Lecturer.fxml";
                    break;
                case "Technical Officer":
                    fxmlPath = "TechnicalOfficer/TechnicalOfficer.fxml";
                    break;
                case "Student":
                    fxmlPath = "Student/Student.fxml";
                    break;
                default:
                    showError("Unknown user role: " + user.getRole());
                    return;
            }
            
            FormUtil.switchScene(currentStage, fxmlPath, null);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading dashboard: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}