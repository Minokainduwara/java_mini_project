package com.example.app.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Timetable {
    private StringProperty timetableId;
    private StringProperty semesterId;
    private StringProperty department;
    private StringProperty filePath;

    public Timetable(String timetableId, String semesterId, String department, String filePath) {
        this.timetableId = new SimpleStringProperty(timetableId);
        this.semesterId = new SimpleStringProperty(semesterId);
        this.department = new SimpleStringProperty(department);
        this.filePath = new SimpleStringProperty(filePath);
    }

    // Getter and Setter methods for each property
    public String getTimetableId() {
        return timetableId.get();
    }

    public void setTimetableId(String timetableId) {
        this.timetableId.set(timetableId);
    }

    public StringProperty timetableIdProperty() {
        return timetableId;
    }

    public String getSemesterId() {
        return semesterId.get();
    }

    public void setSemesterId(String semesterId) {
        this.semesterId.set(semesterId);
    }

    public StringProperty semesterIdProperty() {
        return semesterId;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public StringProperty departmentProperty() {
        return department;
    }

    public String getFilePath() {
        return filePath.get();
    }

    public void setFilePath(String filePath) {
        this.filePath.set(filePath);
    }

    public StringProperty filePathProperty() {
        return filePath;
    }
}
