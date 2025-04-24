package com.example.app.Controllers.Admin;

import com.example.app.Models.Timetable;
import com.example.app.Models.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class TimetableController {
    @FXML private TextField semesterIdField;
    @FXML private SplitMenuButton DepId;
    @FXML private TableView<Timetable> tableView;
    @FXML private TableColumn<Timetable, String> SemCol;
    @FXML private TableColumn<Timetable, String> DepCol;
    @FXML private TableColumn<Timetable, String> linkCol;
    @FXML private TextField timetableIdField;

    private File selectedPDF;

    @FXML
    public void initialize() {

        MenuItem department1 = new MenuItem("ET");
        MenuItem department2 = new MenuItem("ICT");
        MenuItem department3 = new MenuItem("BST");
        DepId.getItems().addAll(department1, department2, department3);

        department1.setOnAction(e -> DepId.setText(department1.getText()));
        department2.setOnAction(e -> DepId.setText(department2.getText()));
        department3.setOnAction(e -> DepId.setText(department3.getText()));

        loadTimetables();
        SemCol.setCellValueFactory(cellData -> cellData.getValue().semesterIdProperty());
        DepCol.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        linkCol.setCellValueFactory(cellData -> cellData.getValue().filePathProperty());


    }

    @FXML
    public void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        selectedPDF = fileChooser.showOpenDialog(new Stage());
        if (selectedPDF != null) {
            System.out.println("Selected PDF: " + selectedPDF.getAbsolutePath());
        } else {
            System.out.println("No file selected.");
        }
    }

    @FXML
    public void handleAdd(ActionEvent event) {
        String id = timetableIdField.getText();
        String sem = semesterIdField.getText();
        String dept = DepId.getText();

        // Check if all fields are filled and a file is selected
        if (selectedPDF == null) {
            showError("Please select a PDF file.");
            return;
        }

        if (id.isEmpty()) {
            showError("Please enter a timetable ID.");
            return;
        }

        if (sem.isEmpty()) {
            showError("Please enter a semester ID.");
            return;
        }

        if (dept.equals("Department")) {  // Default value, adjust as per your logic
            showError("Please select a department.");
            return;
        }

        if (selectedPDF != null && !id.isEmpty() && !sem.isEmpty() && !dept.equals("Department")) {
            String sql = "INSERT INTO timetable (timetable_id, semester, department, pdf_path) VALUES (?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection()) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.setString(2, sem);
                ps.setString(3, dept);
                ps.setString(4, selectedPDF.getAbsolutePath());
                ps.executeUpdate();
                loadTimetables();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add timetable. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Data");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure all fields are filled and a PDF is selected.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleView(ActionEvent event) {
        Timetable selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                File pdfFile = new File(selected.getFilePath());
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("File Not Found");
                    alert.setHeaderText(null);
                    alert.setContentText("The PDF file could not be found at the specified location.");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Opening File");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while trying to open the PDF.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void handleDelete(ActionEvent event) {
        Timetable selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                String sql = "DELETE FROM timetable WHERE timetable_id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, selected.getTimetableId());
                ps.executeUpdate();
                loadTimetables();
            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete timetable. Please try again.");
                alert.showAndWait();
            }
        }
    }

    private void loadTimetables() {
        ObservableList<Timetable> list = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM timetable";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Timetable(
                        rs.getString("timetable_id"),
                        rs.getString("semester"),
                        rs.getString("department"),
                        rs.getString("pdf_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load timetables. Please try again.");
            alert.showAndWait();
        }
        tableView.setItems(list);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
