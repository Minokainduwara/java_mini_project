package com.example.app.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.control.Label;


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

    public void LoginBtnOnAction(javafx.event.ActionEvent actionEvent) {

        //Validation
        if (userNameField.getText().isBlank() == false && PasswordField.getText().isBlank() == false) {
            loginErrormsg.setText("You try to login again");
        } else {
            loginErrormsg.setText("Please enter your username and password");
        }
    }

    public void CancelBtnOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }
}
