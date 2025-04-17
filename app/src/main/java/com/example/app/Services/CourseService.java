package com.example.app.Services;

import com.example.app.Models.Course;
import com.example.app.Util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    public List<Course> getCoursesByDepartment(String department) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Course WHERE department = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public List<String> getAllDepartments() {
        List<String> departments = new ArrayList<>();
        String query = "SELECT DISTINCT department FROM Course";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                departments.add(rs.getString("department"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCode(rs.getString("code"));
        course.setName(rs.getString("name"));
        course.setCredits(rs.getDouble("credits"));
        course.setTheoryHours(rs.getInt("theory_hours"));
        course.setPracticalHours(rs.getInt("practical_hours"));
        course.setSemester(rs.getInt("semester"));
        course.setDepartment(rs.getString("department"));
        return course;
    }
}