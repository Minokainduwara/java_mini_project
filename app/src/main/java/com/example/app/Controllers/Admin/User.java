package com.example.app.Controllers.Admin;

import com.example.app.Models.DatabaseConnection;
import com.example.app.Models.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;

public class User {
    @FXML private TextField userNameField;
    @FXML private TextField RegNoField;
    @FXML private TextField contactNoField;
    @FXML private SplitMenuButton userRoleField;
    @FXML private SplitMenuButton depField;
    @FXML private Button addBtn, updateBtn, deleteBtn;
    @FXML private TableView<UserData> tableView;
    @FXML private TableColumn<UserData, Integer> idColumn;
    @FXML private TableColumn<UserData, String> userNameColumn, roleColumn, departmentColumn;
    @FXML private TableColumn<UserData, String> regNoColumn, contactColumn;

    private Connection conn;

    public void initialize() {
        connectToDatabase();

        idColumn.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        userNameColumn.setCellValueFactory(data -> data.getValue().userNameProperty());
        roleColumn.setCellValueFactory(data -> data.getValue().roleProperty());
        departmentColumn.setCellValueFactory(data -> data.getValue().departmentProperty());
        regNoColumn.setCellValueFactory(data -> data.getValue().regNoProperty());
        contactColumn.setCellValueFactory(data -> data.getValue().contactProperty());

        for (MenuItem item : userRoleField.getItems()) {
            item.setOnAction(e -> userRoleField.setText(item.getText()));
        }
        for (MenuItem item : depField.getItems()) {
            item.setOnAction(e -> depField.setText(item.getText()));
        }

        addBtn.setOnAction(e -> addUser());
        updateBtn.setOnAction(e -> updateUser());
        deleteBtn.setOnAction(e -> deleteUser());

        loadTableData();
    }

    private void connectToDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        conn = connectNow.getConnection();
    }

    private void loadTableData() {
        tableView.getItems().clear();

        String sql = "SELECT id, username, role, department, registration_number, phone FROM User";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserData userData = new UserData(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("department"),
                        rs.getString("registration_number"),
                        rs.getString("phone")
                );
                tableView.getItems().add(userData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser() {
        String sql = "INSERT INTO User (username, password, name, email, phone, department, role, registration_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO Student (name, reg_num, department) VALUES (?, ?, ?)";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userNameField.getText());
            stmt.setString(2, "defaultpw");
            stmt.setString(3, userNameField.getText());
            stmt.setString(4, userNameField.getText() + "@example.com");
            stmt.setString(5, contactNoField.getText());
            stmt.setString(6, depField.getText());
            stmt.setString(7, userRoleField.getText());
            stmt.setString(8, RegNoField.getText());

            int result = stmt.executeUpdate();
            if (result > 0) {
                showAlert("Success", "User added successfully!");
                loadTableData();
            } else {
                showAlert("Error", "Failed to add user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    private void updateUser() {
        String sql = "UPDATE User SET username = ?, name = ?, email = ?, phone = ?, department = ?, role = ?, registration_number = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userNameField.getText());
            stmt.setString(2, userNameField.getText());
            stmt.setString(3, userNameField.getText() + "@example.com");
            stmt.setString(4, contactNoField.getText());
            stmt.setString(5, depField.getText());
            stmt.setString(6, userRoleField.getText());
            stmt.setString(7, RegNoField.getText());
            stmt.setInt(8, getSelectedUserId());

            int result = stmt.executeUpdate();
            if (result > 0) {
                showAlert("Success", "User updated successfully!");
                loadTableData();
            } else {
                showAlert("Error", "Failed to update user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    private void deleteUser() {
        String sql = "DELETE FROM User WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, getSelectedUserId());

            int result = stmt.executeUpdate();
            if (result > 0) {
                showAlert("Success", "User deleted successfully!");
                loadTableData();
            } else {
                showAlert("Error", "Failed to delete user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private int getSelectedUserId() {
        UserData selectedUser = tableView.getSelectionModel().getSelectedItem();
        return selectedUser != null ? selectedUser.getId() : -1;
    }

    @FXML
    private void handleTableRowClick(MouseEvent event) {
        UserData selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userNameField.setText(selectedUser.getUserName());
            contactNoField.setText(selectedUser.getContact());
            RegNoField.setText(selectedUser.getRegNo());
            userRoleField.setText(selectedUser.getRole());
            depField.setText(selectedUser.getDepartment());
        }
    }
}
