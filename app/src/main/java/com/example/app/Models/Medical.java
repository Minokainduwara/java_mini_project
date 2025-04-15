package com.example.app.Models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Medical {
    private int id;
    private int studentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private String status; // 'Pending', 'Approved', 'Rejected'
    private LocalDateTime submittedDate;
    private Integer approvedBy; // Nullable

    // Constructors
    public Medical() {}

    public Medical(int id, int studentId, LocalDate startDate, LocalDate endDate,
                   String reason, String status) {
        this.id = id;
        this.studentId = studentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.status = status;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(LocalDateTime submittedDate) { this.submittedDate = submittedDate; }

    public Integer getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Integer approvedBy) { this.approvedBy = approvedBy; }
}