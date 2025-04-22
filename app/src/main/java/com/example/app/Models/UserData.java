package com.example.app.Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserData {
    private SimpleIntegerProperty id;
    private SimpleStringProperty userName;
    private SimpleStringProperty role;
    private SimpleStringProperty department;
    private SimpleStringProperty regNo;
    private SimpleStringProperty contact;

    public UserData(int id, String userName, String role, String department, String regNo, String contact) {
        this.id = new SimpleIntegerProperty(id);
        this.userName = new SimpleStringProperty(userName);
        this.role = new SimpleStringProperty(role);
        this.department = new SimpleStringProperty(department);
        this.regNo = new SimpleStringProperty(regNo);
        this.contact = new SimpleStringProperty(contact);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public String getRegNo() {
        return regNo.get();
    }

    public SimpleStringProperty regNoProperty() {
        return regNo;
    }

    public String getContact() {
        return contact.get();
    }

    public SimpleStringProperty contactProperty() {
        return contact;
    }
}
