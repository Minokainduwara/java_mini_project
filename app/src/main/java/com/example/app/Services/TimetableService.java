package com.example.app.Services;

import com.example.app.Models.Timetable;
import com.example.app.Util.DatabaseUtil;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableService {
    public List<Timetable> getTimetableByDepartment(String department) {
        List<Timetable> timetableList = new ArrayList<>();
        String query = "SELECT t.* FROM Timetable t " +
                "JOIN Course c ON t.course_code = c.code " +
                "WHERE c.department = ? " +
                "ORDER BY t.day_of_week, t.start_time";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                timetableList.add(mapResultSetToTimetable(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timetableList;
    }

    public List<Timetable> getTodaySessions(String department) {
        List<Timetable> timetableList = new ArrayList<>();
        String query = "SELECT t.* FROM Timetable t " +
                "JOIN Course c ON t.course_code = c.code " +
                "WHERE c.department = ? AND t.day_of_week = ? " +
                "ORDER BY t.start_time";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            stmt.setString(2, getDayOfWeek());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                timetableList.add(mapResultSetToTimetable(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timetableList;
    }

    public List<Timetable> getTimetableByDepartmentAndSemester(String department, Integer semester) {
        List<Timetable> timetableList = new ArrayList<>();
        String query = "SELECT t.* FROM Timetable t " +
                "JOIN Course c ON t.course_code = c.code " +
                "WHERE c.department = ? AND c.semester = ? " +
                "ORDER BY t.day_of_week, t.start_time";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            stmt.setInt(2, semester);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                timetableList.add(mapResultSetToTimetable(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timetableList;
    }

    /**
     * Gets timetable for a specific course
     * @param courseCode the course code to retrieve timetable for
     * @return List of timetable entries for the course
     */
    public List<Timetable> getTimetableByCourse(String courseCode) {
        List<Timetable> timetableList = new ArrayList<>();
        String query = "SELECT t.* FROM Timetable t " +
                "WHERE t.course_code = ? " +
                "ORDER BY t.day_of_week, t.start_time";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, courseCode);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                timetableList.add(mapResultSetToTimetable(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timetableList;
    }

    /**
     * Gets all active courses for a department in the current semester
     * @param department the department
     * @return List of course codes
     */
    public List<String> getActiveCourses(String department) {
        List<String> courses = new ArrayList<>();
        String query = "SELECT DISTINCT t.course_code FROM Timetable t " +
                "JOIN Course c ON t.course_code = c.code " +
                "WHERE c.department = ? " +
                "ORDER BY t.course_code";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("course_code"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    /**
     * Gets the lecturer name for a given lecturer ID
     * @param lecturerId the lecturer ID
     * @return lecturer name or "Unknown" if not found
     */
    public String getLecturerName(Integer lecturerId) {
        if (lecturerId == null) {
            return "Unknown";
        }
        
        String query = "SELECT name FROM User WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, lecturerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "Unknown";
    }
    
    /**
     * Formats a timetable entry as a string
     * @param timetable the timetable entry to format
     * @return formatted string with course code, session type, venue, and lecturer
     */
    public String formatTimetableEntry(Timetable timetable) {
        if (timetable == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(timetable.getCourseCode()).append("\n");
        sb.append(timetable.getSessionType()).append("\n");
        sb.append(timetable.getVenue()).append("\n");
        
        // Add lecturer info if available
        if (timetable.getLecturerId() != null) {
            String lecturerName = getLecturerName(timetable.getLecturerId());
            sb.append(lecturerName);
        }
        
        return sb.toString();
    }

    /**
     * Gets the time slots in the timetable
     * @return List of time slots in format "HH:MM-HH:MM"
     */
    public List<String> getTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        String query = "SELECT DISTINCT DATE_FORMAT(start_time, '%H:%i') as start, " +
                "DATE_FORMAT(end_time, '%H:%i') as end " +
                "FROM Timetable ORDER BY start_time";
                
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                timeSlots.add(rs.getString("start") + "-" + rs.getString("end"));
            }
            
            // If no time slots found, return default time slots
            if (timeSlots.isEmpty()) {
                timeSlots.add("08:00-09:00");
                timeSlots.add("09:00-10:00");
                timeSlots.add("10:00-11:00");
                timeSlots.add("11:00-12:00");
                timeSlots.add("13:00-14:00");
                timeSlots.add("14:00-15:00");
                timeSlots.add("15:00-16:00");
                timeSlots.add("16:00-17:00");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Return default time slots on error
            timeSlots.add("08:00-09:00");
            timeSlots.add("09:00-10:00");
            timeSlots.add("10:00-11:00");
            timeSlots.add("11:00-12:00");
            timeSlots.add("13:00-14:00");
            timeSlots.add("14:00-15:00");
            timeSlots.add("15:00-16:00");
            timeSlots.add("16:00-17:00");
        }
        
        return timeSlots;
    }

    private String getDayOfWeek() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int dayIndex = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        return days[dayIndex];
    }

    private Timetable mapResultSetToTimetable(ResultSet rs) throws SQLException {
        Timetable timetable = new Timetable();
        timetable.setId(rs.getInt("id"));
        timetable.setCourseCode(rs.getString("course_code"));
        timetable.setDayOfWeek(rs.getString("day_of_week"));
        timetable.setStartTime(rs.getTime("start_time").toLocalTime());
        timetable.setEndTime(rs.getTime("end_time").toLocalTime());
        timetable.setVenue(rs.getString("venue"));
        timetable.setSessionType(rs.getString("session_type"));

        if (rs.getObject("lecturer_id") != null) {
            timetable.setLecturerId(rs.getInt("lecturer_id"));
        }

        return timetable;
    }
}