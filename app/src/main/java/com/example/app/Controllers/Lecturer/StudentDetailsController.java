
package com.example.app.Controllers.Lecturer;

import com.example.app.Models.DatabaseConnection;
import com.example.app.Models.StudentDetails;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class StudentDetailsController {

    @FXML private TableView<StudentDetails> studentDetails;
    @FXML private TableColumn<StudentDetails, String> studentName;
    @FXML private TableColumn<StudentDetails, String> regNum;
    @FXML private TableColumn<StudentDetails, Integer> level;
    @FXML private TableColumn<StudentDetails, Float> gpa;
    @FXML private TableColumn<StudentDetails, String> department;
    @FXML private TableColumn<StudentDetails, String> dob;
    @FXML private TableColumn<StudentDetails, String> address;
    @FXML private TableColumn<StudentDetails, String> phoneNumber;
    @FXML private ImageView imageView;
    @FXML private Label noImageLabel;

    private final ObservableList<StudentDetails> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadStudentData();
        setupImageDisplayOnRowSelect();
    }

    private void loadStudentData() {
        DatabaseConnection connectionClass = new DatabaseConnection();
        Connection conn = connectionClass.getConnection();

        String query = "SELECT name, reg_num, level, gpa, department, dob, address, phone, profile_pic FROM student";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                StudentDetails student = new StudentDetails(
                        rs.getString("name"),
                        rs.getString("reg_num"),
                        rs.getInt("level"),
                        rs.getFloat("gpa"),
                        rs.getString("department"),
                        rs.getString("dob"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getBytes("profile_pic")
                );
                data.add(student);
            }

            studentDetails.setItems(data);

            studentName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
            regNum.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRegNum()));
            level.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getLevel()).asObject());
            gpa.setCellValueFactory(cell -> new SimpleFloatProperty(cell.getValue().getGpa()).asObject());
            department.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDepartment()));
            dob.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDob()));
            address.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAddress()));
            phoneNumber.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPhone()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupImageDisplayOnRowSelect() {
        studentDetails.setOnMouseClicked((MouseEvent event) -> {
            StudentDetails selectedStudent = studentDetails.getSelectionModel().getSelectedItem();
            if (selectedStudent != null) {
                byte[] imageBytes = selectedStudent.getProfilePic();
                if (imageBytes != null) {
                    Image img = new Image(new ByteArrayInputStream(imageBytes));
                    imageView.setImage(img);
                    noImageLabel.setVisible(false);
                } else {
                    imageView.setImage(null);
                    noImageLabel.setText("No Image Available");
                    noImageLabel.setVisible(true);
                }
            }
        });
    }

    /*
    @FXML
    private void uploadImageToDatabase() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile)) {

                DatabaseConnection connectionClass = new DatabaseConnection();
                Connection conn = connectionClass.getConnection();

                String query = "INSERT INTO student (name, reg_num, level, gpa, department, dob, address, phone, profile_pic) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, "Jane Smith");
                ps.setString(2, "TG5566");
                ps.setInt(3, 3);
                ps.setFloat(4, 3.8f);
                ps.setString(5, "CS");
                ps.setDate(6, Date.valueOf("2001-09-22"));
                ps.setString(7, "45 Main Street");
                ps.setString(8, "0777654321");
                ps.setBinaryStream(9, fis, (int) selectedFile.length());

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Image uploaded successfully!");
                }

                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
