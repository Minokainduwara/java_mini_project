package com.example.app.Controllers.Admin;

import com.example.app.Models.Courses;
import com.example.app.Models.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;

public class CoursesController {

    @FXML
    private TextField courseCode, CourseNameField, CreditsField;

    @FXML
    private SplitMenuButton courseTypeField, depField, semField;

    @FXML
    private Button addBtn, updateBtn, deleteBtn;

    @FXML
    private TableView<Courses> tableView;

    @FXML
    private TableColumn<Courses, String> codeTable, courseNameTable, typeColumn, depColumn;

    @FXML
    private TableColumn<Courses, Double> creditsColumn;

    @FXML
    private TableColumn<Courses, Integer> SemColumn;

    private ObservableList<Courses> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize table columns
        codeTable.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        courseNameTable.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        creditsColumn.setCellValueFactory(cellData -> cellData.getValue().creditsProperty().asObject());
        depColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        SemColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty().asObject());

        // Load the course data into the table view
        loadCourses();

        // Add listener to handle row click event to populate form fields
        tableView.setOnMouseClicked(event -> handleTableRowClick());
    }

    // Load all courses from the database
    private void loadCourses() {
        courseList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM Course";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    String code = rs.getString("code");
                    String name = rs.getString("name");
                    double credits = rs.getDouble("credits");
                    String type = rs.getString("Type");
                    int semester = rs.getInt("semester");
                    String department = rs.getString("department");
                    Courses course = new Courses(code, name, credits, type, semester, department);
                    courseList.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.setItems(courseList);
    }

    // Handle table row click to populate the form fields
    @FXML
    private void handleTableRowClick() {
        Courses selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseCode.setText(selectedCourse.getCode());
            CourseNameField.setText(selectedCourse.getName());
            CreditsField.setText(String.valueOf(selectedCourse.getCredits()));

            // Set course type
            for (MenuItem item : courseTypeField.getItems()) {
                if (item.getText().equals(selectedCourse.getType())) {
                    courseTypeField.setText(item.getText());
                }
            }

            // Set department
            for (MenuItem item : depField.getItems()) {
                if (item.getText().equals(selectedCourse.getDepartment())) {
                    depField.setText(item.getText());
                }
            }

            // Set semester
            for (MenuItem item : semField.getItems()) {
                if (item.getText().equals(String.valueOf(selectedCourse.getSemester()))) {
                    semField.setText(item.getText());
                }
            }
        }
    }

    // Handle Add Course button action
    @FXML
    private void handleAddCourse() {
        String code = courseCode.getText();
        String name = CourseNameField.getText();
        double credits = Double.parseDouble(CreditsField.getText());
        String type = courseTypeField.getText();
        int semester = Integer.parseInt(semField.getText());
        String department = depField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Course (code, name, credits, Type, semester, department) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, code);
                ps.setString(2, name);
                ps.setDouble(3, credits);
                ps.setString(4, type);
                ps.setInt(5, semester);
                ps.setString(6, department);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadCourses(); // Refresh the table view
    }

    // Handle Update Course button action
    @FXML
    private void handleUpdateCourse() {
        String code = courseCode.getText();
        String name = CourseNameField.getText();
        double credits = Double.parseDouble(CreditsField.getText());
        String type = courseTypeField.getText();
        int semester = Integer.parseInt(semField.getText());
        String department = depField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "UPDATE Course SET name = ?, credits = ?, Type = ?, semester = ?, department = ? WHERE code = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setDouble(2, credits);
                ps.setString(3, type);
                ps.setInt(4, semester);
                ps.setString(5, department);
                ps.setString(6, code);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadCourses(); // Refresh the table view
    }

    // Handle Delete Course button action
    @FXML
    private void handleDeleteCourse() {
        Courses selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String code = selectedCourse.getCode();

            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "DELETE FROM Course WHERE code = ?";
                try (PreparedStatement ps = conn.prepareStatement(query)) {
                    ps.setString(1, code);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadCourses(); // Refresh the table view
        }
    }
}
