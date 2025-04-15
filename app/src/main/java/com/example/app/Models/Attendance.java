package com.example.app.Models;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private int studentId;
    private String courseCode;
    private LocalDate date;
    private String sessionType; // 'Theory' or 'Practical'
    private boolean isPresent;
    private boolean hasMedical;
    private String notes;
    private int recordedBy;

    // Constructors
    public Attendance() {}

    public Attendance(int id, int studentId, String courseCode, LocalDate date,
                      String sessionType, boolean isPresent, boolean hasMedical,
                      String notes, int recordedBy) {
        this.id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.date = date;
        this.sessionType = sessionType;
        this.isPresent = isPresent;
        this.hasMedical = hasMedical;
        this.notes = notes;
        this.recordedBy = recordedBy;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public boolean isPresent() { return isPresent; }
    public void setPresent(boolean present) { isPresent = present; }

    public boolean hasMedical() { return hasMedical; }
    public void setHasMedical(boolean hasMedical) { this.hasMedical = hasMedical; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public int getRecordedBy() { return recordedBy; }
    public void setRecordedBy(int recordedBy) { this.recordedBy = recordedBy; }
}