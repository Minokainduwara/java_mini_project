package com.example.app.Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Courses {

    private SimpleStringProperty code;
    private SimpleStringProperty name;
    private SimpleDoubleProperty credits;
    private SimpleStringProperty type;
    private SimpleIntegerProperty semester;
    private SimpleStringProperty department;
    private SimpleIntegerProperty theoryHours;

    // Updated constructor with 7 parameters
    public Courses(String code, String name, double credits, String type, int semester, String department, int theoryHours) {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleDoubleProperty(credits);
        this.type = new SimpleStringProperty(type);
        this.semester = new SimpleIntegerProperty(semester);
        this.department = new SimpleStringProperty(department);
        this.theoryHours = new SimpleIntegerProperty(theoryHours);
    }

    // Getters and setters
    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getCredits() {
        return credits.get();
    }

    public void setCredits(double credits) {
        this.credits.set(credits);
    }

    public SimpleDoubleProperty creditsProperty() {
        return credits;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public int getSemester() {
        return semester.get();
    }

    public void setSemester(int semester) {
        this.semester.set(semester);
    }

    public SimpleIntegerProperty semesterProperty() {
        return semester;
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public int getTheoryHours() {
        return theoryHours.get();
    }

    public void setTheoryHours(int theoryHours) {
        this.theoryHours.set(theoryHours);
    }

    public SimpleIntegerProperty theoryHoursProperty() {
        return theoryHours;
    }

    // For display
    @Override
    public String toString() {
        return code.get() + " - " + name.get();
    }

    public String getCourseDetails() {
        return "Code: " + code.get() + ", Name: " + name.get() + ", Credits: " + credits.get() +
                ", Type: " + type.get() + ", Semester: " + semester.get() + ", Department: " + department.get() +
                ", Theory Hours: " + theoryHours.get();
    }
}
