
package com.example.app.Controllers;

import com.example.app.Controllers.Lecturer.LectureController;

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
                int userId = result.getInt("id");
                String role = result.getString("role").toLowerCase().trim(); // e.g., admin, student, etc.
                String userName = result.getString("name");
                String fxmlFile;

                switch (role) {
                    case "admin":
                        fxmlFile = "/Fxml/Admin/Admin.fxml";
                        break;
                    case "student":
                        fxmlFile = "/Fxml/Student/Student.fxml";
                        break;
                    case "technicalofficer":
                        fxmlFile = "/Fxml/TechnicalOfficer/TechnicalOfficer.fxml";
                        break;
                    case "lecturer":
                        fxmlFile = "/Fxml/Lecturer/Lecturer.fxml";
                        break;
                    default:
                        loginErrormsg.setText("Unknown role: " + role);
                        return;
                }

                // Load the appropriate dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                // Pass userId to LecturerController if lecturer
                if ("lecturer".equals(role)) {
                    LectureController controller = loader.getController();
                    controller.setUserId(userId);
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close current login window
                Stage currentStage = (Stage) LoginBtn.getScene().getWindow();
                currentStage.close();

            } else {
                loginErrormsg.setText("Invalid login credentials");
            }

        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            loginErrormsg.setText("Something went wrong while logging in.");
            System.out.println(e.getMessage());
        }
    }
}