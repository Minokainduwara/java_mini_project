package com.example.app.Controllers.Lecturer;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NoticesController {

    @FXML
    private TextArea viewNoticeArea;

    @FXML
    private TextArea writeNotice;

    private String latestNotice = "";

    @FXML
    public void initialize() {
        System.out.println("NoticesController initialized.");
        viewNoticeArea.setText("No notices available.");
        viewNoticeArea.setEditable(false);
    }

    @FXML
    private void handleAddNotice() {
        String newNotice = writeNotice.getText();

        if (!newNotice.isEmpty()) {
            latestNotice = newNotice;
            viewNoticeArea.setText(latestNotice);
            System.out.println("Notice added successfully: " + latestNotice);
        } else {
            System.out.println("Notice text is empty.");
        }
    }

    @FXML
    private void handleClearButton() {
        writeNotice.clear();
        System.out.println("Notice input cleared.");
    }
}