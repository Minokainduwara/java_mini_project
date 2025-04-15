package com.example.app.Models;

/**
 * Model class to store attendance statistics for a course module
 */
public class AttendanceStatistics {
    private String courseCode;
    private String sessionType;
    private int totalAttendanceRecords;
    private int presentCount;
    private int absentCount;
    private int medicalLeaveCount;
    private double overallAttendanceRate;
    private int totalSessions;
    private int todaySessions;
    private double todayAttendanceRate;
    private int enrolledStudents;
    private int perfectAttendanceCount;
    private int criticalAttendanceCount;

    public AttendanceStatistics() {
        // Default values
        this.totalAttendanceRecords = 0;
        this.presentCount = 0;
        this.absentCount = 0;
        this.medicalLeaveCount = 0;
        this.overallAttendanceRate = 0.0;
        this.totalSessions = 0;
        this.todaySessions = 0;
        this.todayAttendanceRate = 0.0;
        this.enrolledStudents = 0;
        this.perfectAttendanceCount = 0;
        this.criticalAttendanceCount = 0;
    }

    // Getters and setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public int getTotalAttendanceRecords() {
        return totalAttendanceRecords;
    }

    public void setTotalAttendanceRecords(int totalAttendanceRecords) {
        this.totalAttendanceRecords = totalAttendanceRecords;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getMedicalLeaveCount() {
        return medicalLeaveCount;
    }

    public void setMedicalLeaveCount(int medicalLeaveCount) {
        this.medicalLeaveCount = medicalLeaveCount;
    }

    public double getOverallAttendanceRate() {
        return overallAttendanceRate;
    }

    public void setOverallAttendanceRate(double overallAttendanceRate) {
        this.overallAttendanceRate = overallAttendanceRate;
    }

    public int getTotalSessions() {
        return totalSessions;
    }

    public void setTotalSessions(int totalSessions) {
        this.totalSessions = totalSessions;
    }

    public int getTodaySessions() {
        return todaySessions;
    }

    public void setTodaySessions(int todaySessions) {
        this.todaySessions = todaySessions;
    }

    public double getTodayAttendanceRate() {
        return todayAttendanceRate;
    }

    public void setTodayAttendanceRate(double todayAttendanceRate) {
        this.todayAttendanceRate = todayAttendanceRate;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getPerfectAttendanceCount() {
        return perfectAttendanceCount;
    }

    public void setPerfectAttendanceCount(int perfectAttendanceCount) {
        this.perfectAttendanceCount = perfectAttendanceCount;
    }

    public int getCriticalAttendanceCount() {
        return criticalAttendanceCount;
    }

    public void setCriticalAttendanceCount(int criticalAttendanceCount) {
        this.criticalAttendanceCount = criticalAttendanceCount;
    }

    /**
     * Get formatted overall attendance rate as string with percentage symbol
     */
    public String getFormattedOverallAttendanceRate() {
        return String.format("%.1f%%", overallAttendanceRate);
    }

    /**
     * Get formatted today's attendance rate as string with percentage symbol
     */
    public String getFormattedTodayAttendanceRate() {
        return String.format("%.1f%%", todayAttendanceRate);
    }
}