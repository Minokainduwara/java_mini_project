package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;

public class LectureMaterialController {
    @FXML
    private Button deleteMaterialButton;

    @FXML
    private Button saveMaterialButton;

    @FXML
    private Button uploadMaterialButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ListView<String> materialListView;

    private File uploadedFile;
    private String uploadedFilePath;

    @FXML
    private void handleUploadFile() {

    }
    @FXML
    private void handleSaveMaterial() {

    }

    @FXML
    private void handleDeleteMaterial() {

    }


}
