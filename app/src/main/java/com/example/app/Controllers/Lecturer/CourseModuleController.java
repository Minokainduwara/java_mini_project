
package com.example.app.Controllers.Lecturer;

import com.example.app.Models.Courses;
import com.example.app.Models.DatabaseConnection;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseModuleController {

    // FXML injected fields
    @FXML private TextField CourseNameField;
    @FXML private TextField courseCode;
    @FXML private TextField CreditsField;
    @FXML private ComboBox<String> ctypeid;
    @FXML private ComboBox<String> depID;
    @FXML private ComboBox<String> SemID;
    @FXML private TextField TheoryHoursField;

    @FXML private Button addBtn;
    @FXML private Button updateBtn;
    @FXML private Button deleteBtn;

    @FXML private TableView<Courses> tableView;
    @FXML private TableColumn<Courses, String> codeTable;
    @FXML private TableColumn<Courses, String> courseNameTable;
    @FXML private TableColumn<Courses, String> typeColumn;
    @FXML private TableColumn<Courses, Double> creditsColumn;
    @FXML private TableColumn<Courses, String> depColumn;
    @FXML private TableColumn<Courses, Integer> SemColumn;
    @FXML private TableColumn<Courses, Integer> TheoryHoursCol;

    // Observable list for courses
    private ObservableList<Courses> courseList = FXCollections.observableArrayList();
    private int userId;

    @FXML
    public void initialize() {
        // Set up ComboBoxes (example values, replace with actual options)
        ctypeid.setItems(FXCollections.observableArrayList("Theory", "Practical", "Lab"));
        depID.setItems(FXCollections.observableArrayList("ICT", "ET", "BST","Multi"));
        SemID.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8"));

        // Set up TableView columns
        codeTable.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        courseNameTable.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        creditsColumn.setCellValueFactory(cellData -> cellData.getValue().creditsProperty().asObject());
        depColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        SemColumn.setCellValueFactory(cellData -> cellData.getValue().semesterProperty().asObject());
        TheoryHoursCol.setCellValueFactory(cellData -> cellData.getValue().theoryHoursProperty().asObject());

        // Load initial data into table
        loadCourseData();
    }

    // Method to load course data into the table
    private void loadCourseData() {
        courseList.clear();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM course";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Courses course = new Courses(
                            resultSet.getString("code"),
                            resultSet.getString("name"),
                            resultSet.getDouble("credits"),
                            resultSet.getString("type"),
                            resultSet.getInt("semester"),
                            resultSet.getString("department"),
                            resultSet.getInt("theory_hours")
                    );
                    courseList.add(course);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(courseList);
    }

    // Add new course
    @FXML
    private void handleAddCourse(ActionEvent event) {
        String name = CourseNameField.getText();
        String code = courseCode.getText();
        double credits = Double.parseDouble(CreditsField.getText());
        String type = ctypeid.getValue();
        int semester = Integer.parseInt(SemID.getValue().substring(SemID.getValue().length() - 1));
        String department = depID.getValue();
        int theoryHours = Integer.parseInt(TheoryHoursField.getText());

        String query = "INSERT INTO course (code, name, credits, type, semester, department, theory_hours) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setDouble(3, credits);
            ps.setString(4, type);
            ps.setInt(5, semester);
            ps.setString(6, department);
            ps.setInt(7, theoryHours);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course added successfully!");
                loadCourseData();  // Reload data
            } else {
                System.out.println("Failed to add course.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update an existing course
    @FXML
    private void handleUpdateCourse(ActionEvent event) {
        Courses selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String name = CourseNameField.getText();
            String code = courseCode.getText();
            double credits = Double.parseDouble(CreditsField.getText());
            String type = ctypeid.getValue();
            int semester = Integer.parseInt(SemID.getValue().substring(SemID.getValue().length() - 1));
            String department = depID.getValue();
            int theoryHours = Integer.parseInt(TheoryHoursField.getText());

            String query = "UPDATE course SET name = ?, credits = ?, type = ?, semester = ?, department = ?, theory_hours = ? " +
                    "WHERE code = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, name);
                ps.setDouble(2, credits);
                ps.setString(3, type);
                ps.setInt(4, semester);
                ps.setString(5, department);
                ps.setInt(6, theoryHours);
                ps.setString(7, code);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Course updated successfully!");
                    loadCourseData();  // Reload data
                } else {
                    System.out.println("Failed to update course.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Delete a course
    @FXML
    private void handleDeleteCourse(ActionEvent event) {
        Courses selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String code = selectedCourse.getCode();
            String query = "DELETE FROM course WHERE code = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, code);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Course deleted successfully!");
                    loadCourseData();  // Reload data
                } else {
                    System.out.println("Failed to delete course.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Handle row selection
    @FXML
    private void handleTableRowClick(MouseEvent event) {
        Courses selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            CourseNameField.setText(selectedCourse.getName());
            courseCode.setText(selectedCourse.getCode());
            CreditsField.setText(String.valueOf(selectedCourse.getCredits()));
            ctypeid.setValue(selectedCourse.getType());
            depID.setValue(selectedCourse.getDepartment());
            SemID.setValue("Semester " + selectedCourse.getSemester());
            TheoryHoursField.setText(String.valueOf(selectedCourse.getTheoryHours()));
        }
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
