package com.example.app.Models;

public class Course {
    private String code;
    private String name;
    private double credits;
    private int theoryHours;
    private int practicalHours;
    private int semester;
    private String department;

    // Constructors
    public Course() {}

    public Course(String code, String name, double credits, int theoryHours,
                  int practicalHours, int semester, String department) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.theoryHours = theoryHours;
        this.practicalHours = practicalHours;
        this.semester = semester;
        this.department = department;
    }

    // Getters and setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCredits() { return credits; }
    public void setCredits(double credits) { this.credits = credits; }

    public int getTheoryHours() { return theoryHours; }
    public void setTheoryHours(int theoryHours) { this.theoryHours = theoryHours; }

    public int getPracticalHours() { return practicalHours; }
    public void setPracticalHours(int practicalHours) { this.practicalHours = practicalHours; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}