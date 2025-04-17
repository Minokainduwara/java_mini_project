// src/main/java/com/example/app/Models/Student.java
package com.example.app.Models;

public class Student {
    private int id;
    private String regNumber;
    private String name;
    private String department;
    private int batch;

    public Student(int batch, String department, String name, String regNumber, int id) {
        this.batch = batch;
        this.department = department;
        this.name = name;
        this.regNumber = regNumber;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }
}
