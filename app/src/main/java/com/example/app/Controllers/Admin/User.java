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
        String sql1 = "INSERT INTO User (username, password, name, email, phone, department, role, registration_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO Student (name, reg_num, department) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {

                // Insert into User table
                stmt1.setString(1, userNameField.getText());
                stmt1.setString(2, "defaultpw"); // default password
                stmt1.setString(3, userNameField.getText());
                stmt1.setString(4, userNameField.getText() + "@example.com");
                stmt1.setString(5, contactNoField.getText());
                stmt1.setString(6, depField.getText());
                stmt1.setString(7, userRoleField.getText());
                stmt1.setString(8, RegNoField.getText());

                int result1 = stmt1.executeUpdate();

                boolean studentInserted = true;

                // Only insert into Student table if role is 'student'
                if ("student".equalsIgnoreCase(userRoleField.getText())) {
                    try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                        stmt2.setString(1, userNameField.getText());
                        stmt2.setString(2, RegNoField.getText());
                        stmt2.setString(3, depField.getText());
                        int result2 = stmt2.executeUpdate();
                        studentInserted = result2 > 0;
                    }
                }

                if (result1 > 0 && studentInserted) {
                    conn.commit();
                    showAlert("Success", "User added successfully!");
                    loadTableData();
                } else {
                    conn.rollback();
                    showAlert("Error", "Failed to add user/student. Transaction rolled back.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateUser() {
        String sql1 = "UPDATE User SET username = ?, name = ?, email = ?, phone = ?, department = ?, role = ?, registration_number = ? WHERE id = ?";
        String sql2 = "UPDATE Student SET name = ?, reg_num = ?, department = ? WHERE reg_num = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            UserData selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlert("Error", "No user selected.");
                return;
            }

            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

                // Update User table
                stmt1.setString(1, userNameField.getText());
                stmt1.setString(2, userNameField.getText());
                stmt1.setString(3, userNameField.getText() + "@example.com");
                stmt1.setString(4, contactNoField.getText());
                stmt1.setString(5, depField.getText());
                stmt1.setString(6, userRoleField.getText());
                stmt1.setString(7, RegNoField.getText());
                stmt1.setInt(8, selectedUser.getId());

                int result1 = stmt1.executeUpdate();

                // Update Student table
                stmt2.setString(1, userNameField.getText());
                stmt2.setString(2, RegNoField.getText());
                stmt2.setString(3, depField.getText());
                stmt2.setString(4, selectedUser.getRegNo()); // Where old reg_num

                int result2 = stmt2.executeUpdate();

                if (result1 > 0 && result2 > 0) {
                    conn.commit();
                    showAlert("Success", "User and Student updated successfully!");
                    loadTableData();
                } else {
                    conn.rollback();
                    showAlert("Error", "Failed to update user/student. Transaction rolled back.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteUser() {
        String sql1 = "DELETE FROM Student WHERE reg_num = ?";
        String sql2 = "DELETE FROM User WHERE id = ?";

        try {
            conn.setAutoCommit(false); // Start transaction

            UserData selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser == null) {
                showAlert("Error", "No user selected.");
                return;
            }

            try (PreparedStatement stmt1 = conn.prepareStatement(sql1);
                 PreparedStatement stmt2 = conn.prepareStatement(sql2)) {

                // Delete from Student table
                stmt1.setString(1, selectedUser.getRegNo());
                stmt1.executeUpdate();

                // Delete from User table
                stmt2.setInt(1, selectedUser.getId());
                stmt2.executeUpdate();

                conn.commit();
                showAlert("Success", "User and Student deleted successfully!");
                loadTableData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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