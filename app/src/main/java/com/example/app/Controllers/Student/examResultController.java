package com.example.app.Controllers.Student;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class examResultController {

    @FXML
    private TextField CGPAvalue;

    @FXML
    private TableColumn<?, ?> Result;

    @FXML
    private TextField SGPAValue;

    @FXML
    private TableView<?> examResultTable;

    @FXML
    private TableColumn<?, ?> subjectCode;

    @FXML
    private TableColumn<?, ?> subjectName;

}