package com.example.app.Controllers;

import com.example.app.Models.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private Button LoginBtn;

    @FXML
    private Button CancelBtn;

    @FXML
    private Label loginErrormsg;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField PasswordField;

    // Called when Login button is clicked
    public void LoginBtnOnAction(javafx.event.ActionEvent actionEvent) {
        String username = userNameField.getText().trim();
        String password = PasswordField.getText().trim();

        if (!username.isBlank() && !password.isBlank()) {
            validateLogin(username, password);
        } else {
            loginErrormsg.setText("Please enter your username and password");
        }
    }

    // Called when Cancel button is clicked
    public void CancelBtnOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }

    // Login validation logic
    public void validateLogin(String username, String password) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM User WHERE username = ? AND password = ?";

        try {
            PreparedStatement statement = connectDB.prepareStatement(verifyLogin);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String role = result.getString("role"); // e.g., Admin, Student, etc.
                loginErrormsg.setText("Login successful! Role: " + role);

                // Example: Load a new dashboard view
                // Uncomment and customize this if you have a dashboard.fxml
                /*
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/app/Views/Dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close current login window
                Stage currentStage = (Stage) LoginBtn.getScene().getWindow();
                currentStage.close();
                */

            } else {
                loginErrormsg.setText("Invalid login credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Database error occurred" + e.getMessage());
            loginErrormsg.setText("Database error occurred" + e.getMessage());
            System.out.println("Database error occurred" + e.getMessage());
        }
    }
}
