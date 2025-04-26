package com.example.app.Controllers.Admin;
import com.example.app.Models.DatabaseConnection;
import com.example.app.Models.Notice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NoticesController {

    @FXML
    private TextArea writeNotice;

    @FXML
    private TextField titleField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<Notice> NoticeTable;

    @FXML
    private TableColumn<Notice, Integer> noticeID;

    @FXML
    private TableColumn<Notice, String> TitleCol;

    @FXML
    private TableColumn<Notice, String> ContentCol;

    private ObservableList<Notice> noticeList = FXCollections.observableArrayList();

    private Connection conn;

    @FXML
    public void initialize() {
        conn = DatabaseConnection.getConnection();

        noticeID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        TitleCol.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        ContentCol.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        loadNotices();

        NoticeTable.setOnMouseClicked(this::handleRowClick);

        addButton.setOnAction(event -> addNotice());
        updateButton.setOnAction(event -> updateNotice());
        deleteButton.setOnAction(event -> deleteNotice());

        // Disable update and delete buttons initially
        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        // Listen for table selection changes
        NoticeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean selected = newValue != null;
            updateButton.setDisable(!selected);
            deleteButton.setDisable(!selected);
        });
    }

    private void loadNotices() {
        noticeList.clear();
        String sql = "SELECT * FROM notices";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                noticeList.add(new Notice(rs.getInt("id"), rs.getString("title"), rs.getString("content")));
            }
            NoticeTable.setItems(noticeList);
        } catch (SQLException e) {
            showErrorAlert("An error occurred while loading notices.", e);
        }
    }

    private void addNotice() {
        if (areFieldsEmpty()) return;

        String title = titleField.getText();
        String content = writeNotice.getText();

        String sql = "INSERT INTO notices (title, content) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.executeUpdate();
            loadNotices();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Notice added successfully.");
        } catch (SQLException e) {
            showErrorAlert("An error occurred while adding the notice.", e);
        }
    }

    private void updateNotice() {
        Notice selected = NoticeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No notice selected.");
            return;
        }

        if (areFieldsEmpty()) return;

        String title = titleField.getText();
        String content = writeNotice.getText();

        String sql = "UPDATE notices SET title = ?, content = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, selected.getId());
            stmt.executeUpdate();
            loadNotices();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Notice updated successfully.");
        } catch (SQLException e) {
            showErrorAlert("An error occurred while updating the notice.", e);
        }
    }

    private void deleteNotice() {
        Notice selected = NoticeTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No notice selected.");
            return;
        }

        String sql = "DELETE FROM notices WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, selected.getId());
            stmt.executeUpdate();
            loadNotices();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Notice deleted successfully.");
        } catch (SQLException e) {
            showErrorAlert("An error occurred while deleting the notice.", e);
        }
    }

    private void handleRowClick(MouseEvent event) {
        Notice selected = NoticeTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            titleField.setText(selected.getTitle());
            writeNotice.setText(selected.getContent());
        } else {
            clearFields();
        }
    }

    private void clearFields() {
        titleField.clear();
        writeNotice.clear();
        NoticeTable.getSelectionModel().clearSelection();
    }

    private boolean areFieldsEmpty() {
        if (titleField.getText().isEmpty() || writeNotice.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in both title and content.");
            return true;
        }
        return false;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message, SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", message + "\n" + e.getMessage());
    }
}
