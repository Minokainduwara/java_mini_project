package com.example.app.Controllers.Lecturer;

import com.example.app.Models.DatabaseConnection;
import com.example.app.Models.NoticeDisplay;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NoticesController {

    @FXML private TableView<NoticeDisplay> noticeTable;
    @FXML private TableColumn<NoticeDisplay, Integer> noticeId;
    @FXML private TableColumn<NoticeDisplay, String> noticeTitle;
    @FXML private TableColumn<NoticeDisplay, String> noticeContent;
    @FXML private TextArea viewNoticeArea;

    private final ObservableList<NoticeDisplay> noticeData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadNoticeData();
        setupRowSelection();
    }

    private void loadNoticeData() {
        DatabaseConnection connectionClass = new DatabaseConnection();
        Connection conn = connectionClass.getConnection();

        String query = "SELECT id, title, content FROM notices";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");

                NoticeDisplay notice = new NoticeDisplay(id, title, content);
                noticeData.add(notice);
            }

            noticeTable.setItems(noticeData);

            noticeId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
            noticeTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            noticeContent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupRowSelection() {
        noticeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                viewNoticeArea.setText(newSelection.getContent());
            }
        });
    }

    @FXML
    private void handleClearNoticeAreaButton() {
        viewNoticeArea.clear();
    }
}