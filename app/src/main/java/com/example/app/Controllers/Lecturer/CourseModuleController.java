package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class CourseModuleController {
    @FXML
    private Button addModuleButton;

    @FXML
    private Button deleteModuleButton;

    @FXML
    private Button showModuleButton;

    @FXML
    private void initialize() {
        System.out.println("Initializing CourseModuleController");
    }

    @FXML
    private void handleAddModule(){
        System.out.println("handleAddModuleButton");
    }

    @FXML
    private void handleDeleteModule(ActionEvent event) {
        System.out.println("deleteModuleButton");
    }

    @FXML
    private void handleShowModule(ActionEvent event) {
        System.out.println("showModuleButton");
    }
}
