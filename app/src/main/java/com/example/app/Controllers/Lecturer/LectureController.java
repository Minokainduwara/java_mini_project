package com.example.app.Controllers.Lecturer;

import com.example.app.Models.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.scene.image.Image;



public class LectureController {
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        loadProfilePicture();
        loadUsername();
    }
    @FXML
    private AnchorPane paneCenter;

    private void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Lecturer/" + page + ".fxml"));
            Pane view = loader.load();

            if (page.equals("LectureMaterial")) {
                LectureMaterialController controller = loader.getController();
                controller.setUserId(userId);
            } else if (page.equals("CourseModule")) {
                CourseModuleController controller = loader.getController();
                controller.setUserId(userId); // Pass userId for CourseModule
            } else if (page.equals("Profile")) {
                ProfileController controller = loader.getController();
                controller.setUserId(userId);
            }

            paneCenter.getChildren().setAll(view);
        } catch (IOException e) {
            System.out.println("Failed to load: " + page);
            e.printStackTrace();
        }
    }
    @FXML
    private ImageView profileImageView;

    /*
    private void loadProfilePicture() {
        String query = "SELECT profile_picture FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("profile_picture");
                if (imageBytes != null && imageBytes.length > 0) {
                    InputStream is = new ByteArrayInputStream(imageBytes);

                    Image tempImage = new Image(is);
                    double origW = tempImage.getWidth();
                    double origH = tempImage.getHeight();

                    boolean isWider = (origW > origH);

                    is = new ByteArrayInputStream(imageBytes);
                    Image image;
                    if (isWider) {
                        image = new Image(is, 0, 130, true, true);
                    } else {
                        image = new Image(is, 130, 0, true, true);
                    }

                    profileImageView.setImage(image);

                    Circle clip = new Circle(65, 65, 65);
                    profileImageView.setClip(clip);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading profile image:");
            e.printStackTrace();
        }
    }*/
    /*
    private void loadProfilePicture() {
        String query = "SELECT profile_picture FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("profile_picture");
                if (imageBytes != null && imageBytes.length > 0) {
                    InputStream is = new ByteArrayInputStream(imageBytes);


                    Image image = new Image(is);


                    double targetSize = Math.min(profileImageView.getFitWidth(),
                            profileImageView.getFitHeight());


                    is = new ByteArrayInputStream(imageBytes);
                    image = new Image(is, targetSize, targetSize, true, true);

                    profileImageView.setImage(image);


                    double padding = 3;
                    Circle clip = new Circle(
                            targetSize / 2,
                            targetSize / 2,
                            (targetSize / 2) - padding
                    );
                    profileImageView.setClip(clip);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading profile image:");
            e.printStackTrace();
        }
    }*/

    private void loadProfilePicture() {
        String query = "SELECT profile_picture FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("profile_picture");
                if (imageBytes != null && imageBytes.length > 0) {
                    InputStream is = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(is);
                    profileImageView.setImage(image);

                    // Apply circular clip
                    //Circle clip = new Circle(
                       //     profileImageView.getFitWidth() / 2,
                        //    profileImageView.getFitHeight() / 2,
                          //  profileImageView.getFitWidth() / 2
                   // );
                   // profileImageView.setClip(clip);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error loading profile image:");
            e.printStackTrace();
        }
    }

    @FXML
    private Label userName;
    private void loadUsername() {
        String query = "SELECT username FROM User WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             var pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                userName.setText(username);
            }
        } catch (SQLException e) {
            System.out.println("Error loading username:");
            e.printStackTrace();
            userName.setText("User");
        }
    }


    @FXML
    private Button profileButton, courseModuleButton, noticesButton, studentDetailsButton, logOutButton;


    @FXML
    private void handleViewProfile(ActionEvent event) {
        loadPage("Profile");
        System.out.println("Profile button pressed");
    }


    @FXML
    private void handleCourseModuleButton(ActionEvent event) {
        System.out.println("Course module button pressed");
        loadPage("CourseModule");
    }

    @FXML
    private void handleNoticesButton(ActionEvent event) {
        System.out.println("Notices button pressed");
        loadPage("Notices");
    }

    @FXML
    private void handleStudentDetailsButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage("StudentDetails");
    }

    @FXML
    private void handleStudentMarksButton(ActionEvent event) {
        System.out.println("Student details button pressed");
        loadPage("Marks");
    }

    @FXML
    private void handleLectureMaterialButton(ActionEvent event) {
        System.out.println("Lecture material button pressed");
        loadPage("LectureMaterial");
    }

    @FXML
    private void handleLogOutButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
