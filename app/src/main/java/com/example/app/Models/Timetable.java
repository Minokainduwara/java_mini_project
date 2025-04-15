package com.example.app.Models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Timetable {
    private int id;
    private String courseCode;
    private String dayOfWeek; // 'Monday', 'Tuesday', etc.
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;
    private String sessionType; // 'Theory' or 'Practical'
    private Integer lecturerId; // Nullable

    // Constructors
    public Timetable() {}

    public Timetable(int id, String courseCode, String dayOfWeek, LocalTime startTime,
                     LocalTime endTime, String venue, String sessionType, Integer lecturerId) {
        this.id = id;
        this.courseCode = courseCode;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.sessionType = sessionType;
        this.lecturerId = lecturerId;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getSessionType() { return sessionType; }
    public void setSessionType(String sessionType) { this.sessionType = sessionType; }

    public Integer getLecturerId() { return lecturerId; }
    public void setLecturerId(Integer lecturerId) { this.lecturerId = lecturerId; }
}