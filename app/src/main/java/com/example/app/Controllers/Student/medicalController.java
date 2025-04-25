package com.example.app.Controllers.Student;

import com.example.app.Models.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

public class medicalController {

    @FXML private TreeTableColumn<MedicalRecord, String> medicalEndDate;
    @FXML private TreeTableColumn<MedicalRecord, String> medicalStatus;
    @FXML private TreeTableColumn<MedicalRecord, String> medicalStartDate;
    @FXML private TreeTableView<MedicalRecord> mediclatable;

    @FXML private DatePicker medicalLastDate;
    @FXML private DatePicker medicalStartDatePicker;
    @FXML private Button medicalReportUploadbtn;
    @FXML private Button medicaleReportSavebtn;

    private File selectedFile;
    private String uploadedFilePath;

    private final String studentId = "S123"; // Replace with actual student ID when integrating login

    @FXML
    void initialize() {
        medicalStartDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("startDate"));
        medicalEndDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("endDate"));
        medicalStatus.setCellValueFactory(new TreeItemPropertyValueFactory<>("status"));

        loadMedicalRecords();
    }

    @FXML
    void handleuploadmedicalreport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Medical Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Window window = medicalReportUploadbtn.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                Path destinationDir = Paths.get("medicals/");
                if (!Files.exists(destinationDir)) {
                    Files.createDirectories(destinationDir);
                }

                String uniqueName = System.currentTimeMillis() + "_" + selectedFile.getName();
                Path destinationPath = destinationDir.resolve(uniqueName);
                Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                uploadedFilePath = destinationPath.toString();
                System.out.println("File uploaded to: " + uploadedFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void handlemedclereportsave(ActionEvent event) {
        LocalDate start = medicalStartDatePicker.getValue();
        LocalDate end = medicalLastDate.getValue();

        if (start == null || end == null || uploadedFilePath == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled and file must be uploaded.");
            return;
        }

        DatabaseConnection db = new DatabaseConnection();
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO medical (student_id, start_date, end_date, file_path, status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentId);
            stmt.setDate(2, Date.valueOf(start));
            stmt.setDate(3, Date.valueOf(end));
            stmt.setString(4, uploadedFilePath);
            stmt.setString(5, "Pending");
            stmt.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Medical report saved successfully.");
            loadMedicalRecords(); // Refresh table
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadMedicalRecords() {
        DatabaseConnection db = new DatabaseConnection();
        try (Connection conn = db.getConnection()) {
            String query = "SELECT start_date, end_date, status FROM medical WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();

            TreeItem<MedicalRecord> root = new TreeItem<>(new MedicalRecord("", "", ""));
            while (rs.next()) {
                String start = rs.getDate("start_date").toString();
                String end = rs.getDate("end_date").toString();
                String status = rs.getString("status");
                root.getChildren().add(new TreeItem<>(new MedicalRecord(start, end, status)));
            }
            mediclatable.setRoot(root);
            mediclatable.setShowRoot(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class MedicalRecord {
        private final StringProperty startDate;
        private final StringProperty endDate;
        private final StringProperty status;

        public MedicalRecord(String startDate, String endDate, String status) {
            this.startDate = new SimpleStringProperty(startDate);
            this.endDate = new SimpleStringProperty(endDate);
            this.status = new SimpleStringProperty(status);
        }

        public String getStartDate() { return startDate.get(); }
        public String getEndDate() { return endDate.get(); }
        public String getStatus() { return status.get(); }
    }
}
