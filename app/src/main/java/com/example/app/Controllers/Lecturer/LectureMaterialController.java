package com.example.app.Controllers.Lecturer;
import com.example.app.Models.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.*;
import java.util.stream.IntStream;

public class LectureMaterialController {

    @FXML private TextField titleField;
    @FXML private TextArea descriptionArea;
    @FXML private ComboBox<Integer> weekComboBox;
    @FXML private ListView<MaterialItem> materialListView;

    private String filePath = null;
    private int userId;

    public void setUserId(int id) {
        this.userId = id;
        loadMaterials();
    }

    @FXML
    public void initialize() {
        IntStream.rangeClosed(1, 15).forEach(weekComboBox.getItems()::add);
        //loadMaterials();
    }

    @FXML
    public void handleUploadFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePath = file.getAbsolutePath();
        }
    }

    @FXML
    public void handleSaveMaterial() {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        Integer weekNumber = weekComboBox.getValue();

        if (title.isEmpty() || description.isEmpty() || weekNumber == null || filePath == null) {
            showAlert("Error", "All fields including file upload must be completed.");
            return;
        }

        if (!isValidUserId(userId)) {
            showAlert("Error", "Invalid User ID.");
            return;
        }

        try (Connection conn = new DatabaseConnection().getConnection()) {
            String insertQuery = "INSERT INTO Lecture_Material (user_id, title, description, file_path, week_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, filePath);
            stmt.setInt(5, weekNumber);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    int id = keys.getInt(1);
                    //materialListView.getItems().add(new MaterialItem(id, title + " - Week " + weekNumber));
                    loadMaterials();
                }
                clearForm();
                showAlert("Success", "Material saved successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Could not save material: " + e.getMessage());
        }
    }

    @FXML
    public void handleDeleteMaterial() {
        MaterialItem selected = materialListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try (Connection connection = new DatabaseConnection().getConnection()) {
                String deleteQuery = "DELETE FROM Lecture_Material WHERE material_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, selected.getId());
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        materialListView.getItems().remove(selected);
                        showAlert("Deleted", "Item removed from list and database.");
                    } else {
                        showAlert("Not Found", "The selected material was not found in the database.");
                    }
                }
            } catch (SQLException e) {
                showAlert("Error", "An error occurred while deleting the material: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                showAlert("Error", "An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("No Selection", "Please select a material to delete.");
        }
    }

    private void loadMaterials() {
        try (Connection conn = new DatabaseConnection().getConnection()) {
            String query = "SELECT material_id, title, week_number, description FROM Lecture_Material WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            materialListView.getItems().clear();

            while (rs.next()) {
                int id = rs.getInt("material_id");
                String title = rs.getString("title");
                int week = rs.getInt("week_number");
                String description = rs.getString("description");
                //Timestamp uploadedAt = rs.getTimestamp("uploaded_at");

                //String formattedDate = uploadedAt.toLocalDateTime().toString();

                materialListView.getItems().add(new MaterialItem(id, "Week: " + week + " - " + title  + " - " + description ));

            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load materials: " + e.getMessage());
        }
    }


    private boolean isValidUserId(int userId) {
        String query = "SELECT COUNT(*) FROM user WHERE id = ?";
        try (Connection conn = new DatabaseConnection().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void clearForm() {
        titleField.clear();
        descriptionArea.clear();
        filePath = null;

        weekComboBox.getSelectionModel().clearSelection();
        weekComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Select Week");
                } else {
                    setText("Week " + item);
                }
            }
        });
    }


    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Inner class for holding material data
    public static class MaterialItem {
        private int id;
        private String displayText;

        public MaterialItem(int id, String displayText) {
            this.id = id;
            this.displayText = displayText;
        }

        public int getId() {
            return id;
        }

        public String getDisplayText() {
            return displayText;
        }

        @Override
        public String toString() {
            return displayText;
        }
    }
}