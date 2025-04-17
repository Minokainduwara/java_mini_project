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
import java.sql.*;
import java.io.File;


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
        loadMaterialsFromDatabase();
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
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String filePath = uploadedFile != null ? uploadedFile.getAbsolutePath() : "";

        if (title.isEmpty() || filePath.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Title and file must be selected.");
            alert.show();
            return;
        }

        String sql = "INSERT INTO lecture_materials (title, description, file_path) VALUES (?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, filePath);
            stmt.executeUpdate();

            titleField.clear();
            descriptionArea.clear();
            uploadedFile = null;
            loadMaterialsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void handleDeleteMaterial() {
        String selected = materialListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String title = selected.split(" - ")[0];
        String sql = "DELETE FROM lecture_materials WHERE title = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
            loadMaterialsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMaterialsFromDatabase() {
        materials.clear();
        String sql = "SELECT * FROM lecture_materials";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String entry = rs.getString("title") + " - " + rs.getString("description") + rs.getString("file_path");
                materials.add(entry);
            }
            materialListView.setItems(materials);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
