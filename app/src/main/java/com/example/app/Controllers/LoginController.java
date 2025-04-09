package com.example.app.Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button LoginBtn;

    @FXML
    private Button CancelBtn;

    public void LoginBtnOnAction(ActionEvent event) {


    }

    public void CancelBtnOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) CancelBtn.getScene().getWindow();
        stage.close();
    }
}
