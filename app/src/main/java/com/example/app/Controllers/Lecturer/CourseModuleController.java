package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CourseModuleController {
    @FXML
    private Button addModuleButton;

    @FXML
    private void initialize() {
        System.out.println("Initializing CourseModuleController");
    }

    @FXML
    private void handleAddModuleButton(){
        System.out.println("handleAddModuleButton");
    }
}
