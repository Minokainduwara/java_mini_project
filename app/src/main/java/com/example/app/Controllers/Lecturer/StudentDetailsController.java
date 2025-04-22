package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class StudentDetailsController {

    @FXML
    private Label studentName;

    @FXML
    private Label registrationNumber;

    @FXML
    private Label level;

    @FXML
    private Label gpa;

    @FXML
    private Label department;

    @FXML
    private Label dob;

    @FXML
    private Label address;

    @FXML
    private Label phoneNumber;

    @FXML
    private ImageView imageView;

    @FXML
    private void initialize() {
        imageView.setPreserveRatio(true);

        Circle clip = new Circle(imageView.getFitWidth() / 2,
                imageView.getFitHeight() / 2,
                Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2);
        imageView.setClip(clip);

        System.out.println("StudentDetailsController initialized");
    }

    public void setStudentDetails(String name, String regNo, String levelText, String gpaText,
                                  String dept, String birthDate, String addr, String phone, Image profileImage) {
        studentName.setText(name);
        registrationNumber.setText(regNo);
        level.setText(levelText);
        gpa.setText(gpaText);
        department.setText(dept);
        dob.setText(birthDate);
        address.setText(addr);
        phoneNumber.setText(phone);

        if (profileImage != null) {
            imageView.setImage(profileImage);
        }
    }
}