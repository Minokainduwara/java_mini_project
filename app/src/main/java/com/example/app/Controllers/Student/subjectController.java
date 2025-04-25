package com.example.app.Controllers.Student;

import com.example.app.Models.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class subjectController {

    @FXML private TabPane tabPane;

    @FXML private Tab week1tab, week2tab, week3tab, week5tab, week6tab,
            week7tab, week8tab, week9tab, week10tab, week11tab,
            week12tab, week13tab, week14tab, week15tab;

    public void initialize() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null) {
                String tabId = newTab.getId(); // e.g., week1tab
                int weekNumber = extractWeekNumber(tabId);
                loadLectureMaterial(newTab, weekNumber);
            }
        });
    }

    private int extractWeekNumber(String tabId) {
        // Example: week1tab → 1
        return Integer.parseInt(tabId.replaceAll("[^\\d]", ""));
    }

    private void loadLectureMaterial(Tab tab, int weekNumber) {
        DatabaseConnection db = new DatabaseConnection();
        Connection conn = db.getConnection();

        String query = "SELECT title, description FROM Lecture_Material WHERE week_number = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, weekNumber);
            ResultSet rs = stmt.executeQuery();

            Pane contentPane = new Pane();
            contentPane.setPrefSize(682, 418);
            contentPane.setStyle("-fx-background-color: white;");

            double yOffset = 10.0;

            while (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");

                Text titleText = new Text("Title: " + title);
                titleText.setLayoutY(yOffset);
                titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
                yOffset += 25;

                Text descText = new Text("Description: " + description);
                descText.setLayoutY(yOffset);
                yOffset += 60;

                contentPane.getChildren().addAll(titleText, descText);
            }

            tab.setContent(contentPane);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
}
