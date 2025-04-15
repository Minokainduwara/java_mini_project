package com.example.app.Controllers.Lecturer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.control.*;
import javafx.stage.FileChooser;
// import java.sql.*;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LectureMaterialController {
    @FXML
    private Button deleteMaterialButton,saveMaterialButton,uploadMaterialButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ListView<String> materialListView;

    private File uploadedFile;
    private final ObservableList<String> materials = FXCollections.observableArrayList();

    private Connection getConnection() throws SQLException {
        // Replace with our DB config
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/Database Name", "root", "");
    }

    @FXML
    private void initialize() {
        //loadMaterialsFromDatabase();
    }

    @FXML
    private void handleUploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        uploadedFile = fileChooser.showOpenDialog(null);

        if (uploadedFile != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "File Selected: " + uploadedFile.getName(), ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    private void handleSaveMaterial() {

    }

    @FXML
    private void handleDeleteMaterial() {

    }


}
