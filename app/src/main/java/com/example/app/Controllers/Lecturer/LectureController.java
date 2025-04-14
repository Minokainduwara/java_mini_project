package com.example.app.Controllers.Lecturer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class LectureController {
    @FXML
    private BorderPane mainPane;

    @FXML
    private Label userName;

    @FXML
    private Button profileButton, homeButton, courseModuleButton, noticesButton, studentDetailsButton, logOutButton;

    /*@FXML
    private void profileButton(ActionEvent event) {
        loadPage("Profile");
    }*/

    /*@FXML
    private void CourseModuleButton(ActionEvent event) {
        loadPage("CourseModule");
    }*/

    /*private void loadPage(String page) {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage(page);
        if (view != null) {
            mainPane.setCenter(view);
        } else {
            System.out.println("Failed to load: " + page);
        }
    }*/

    @FXML
    private void profileButton(ActionEvent event) {

    }

    @FXML
    private void homeButton(ActionEvent event) {

    }

    @FXML
    private void courseModuleButton(ActionEvent event) {

    }

    @FXML
    private void noticesButton(ActionEvent event) {

    }

    @FXML
    private void studentDetailsButton(ActionEvent event) {

    }

    @FXML
    private void logOutButton(ActionEvent event) {

    }

}
