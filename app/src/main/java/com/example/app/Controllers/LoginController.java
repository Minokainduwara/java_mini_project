package com.example.app.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class LoginController {
    @FXML
    private Button LoginBtn;

    @FXML
    private Button CancelBtn;

    @FXML
    private Label loginErrormsg;

    public void LoginBtnOnAction(javafx.event.ActionEvent actionEvent) {
        loginErrormsg.setText("You try to login");
    }

    public void CancelBtnOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }
}
