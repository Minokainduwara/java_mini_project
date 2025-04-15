package com.example.app.Models;

import java.time.LocalDateTime;

public class Mark {
    private int id;
    private int studentId;
    private String courseCode;
    private String assessmentType; // 'Quiz', 'Mid Term', 'Assessment', 'Final Theory', 'Final Practical'
    private int assessmentNumber;
    private double marks;
    private double outOf;
    private int recordedBy;
    private LocalDateTime recordedDate;

    // Constructors
    public Mark() {}

    public Mark(int id, int studentId, String courseCode, String assessmentType,
                int assessmentNumber, double marks, double outOf, int recordedBy) {
        this.id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.assessmentType = assessmentType;
        this.assessmentNumber = assessmentNumber;
        this.marks = marks;
        this.outOf = outOf;
        this.recordedBy = recordedBy;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public int getAssessmentNumber() { return assessmentNumber; }
    public void setAssessmentNumber(int assessmentNumber) { this.assessmentNumber = assessmentNumber; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }

    public double getOutOf() { return outOf; }
    public void setOutOf(double outOf) { this.outOf = outOf; }

    public int getRecordedBy() { return recordedBy; }
    public void setRecordedBy(int recordedBy) { this.recordedBy = recordedBy; }

    public LocalDateTime getRecordedDate() { return recordedDate; }
    public void setRecordedDate(LocalDateTime recordedDate) { this.recordedDate = recordedDate; }
}