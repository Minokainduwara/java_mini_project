package com.example.app.Services;

import com.example.app.Models.Medical;
import com.example.app.Util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalService {
    public List<Medical> getAllMedicals() {
        List<Medical> medicalList = new ArrayList<>();
        String query = "SELECT * FROM Medical ORDER BY submitted_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                medicalList.add(mapResultSetToMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalList;
    }

    public List<Medical> getMedicalsByStudentId(int studentId) {
        List<Medical> medicalList = new ArrayList<>();
        String query = "SELECT * FROM Medical WHERE student_id = ? ORDER BY submitted_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                medicalList.add(mapResultSetToMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalList;
    }

    public boolean addMedical(Medical medical) {
        String query = "INSERT INTO Medical (student_id, start_date, end_date, reason, status, approved_by) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, medical.getStudentId());
            stmt.setDate(2, Date.valueOf(medical.getStartDate()));
            stmt.setDate(3, Date.valueOf(medical.getEndDate()));
            stmt.setString(4, medical.getReason());
            stmt.setString(5, medical.getStatus());

            if (medical.getApprovedBy() != null) {
                stmt.setInt(6, medical.getApprovedBy());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMedical(Medical medical) {
        String query = "UPDATE Medical SET start_date = ?, end_date = ?, reason = ?, status = ?, approved_by = ? " +
                "WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, Date.valueOf(medical.getStartDate()));
            stmt.setDate(2, Date.valueOf(medical.getEndDate()));
            stmt.setString(3, medical.getReason());
            stmt.setString(4, medical.getStatus());

            if (medical.getApprovedBy() != null) {
                stmt.setInt(5, medical.getApprovedBy());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, medical.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Medical mapResultSetToMedical(ResultSet rs) throws SQLException {
        Medical medical = new Medical();
        medical.setId(rs.getInt("id"));
        medical.setStudentId(rs.getInt("student_id"));
        medical.setStartDate(rs.getDate("start_date").toLocalDate());
        medical.setEndDate(rs.getDate("end_date").toLocalDate());
        medical.setReason(rs.getString("reason"));
        medical.setStatus(rs.getString("status"));

        if (rs.getTimestamp("submitted_date") != null) {
            medical.setSubmittedDate(rs.getTimestamp("submitted_date").toLocalDateTime());
        }

        if (rs.getObject("approved_by") != null) {
            medical.setApprovedBy(rs.getInt("approved_by"));
        }

        return medical;
    }

    public List<Medical> getPendingMedicals() {
        List<Medical> medicalList = new ArrayList<>();
        String query = "SELECT * FROM Medical WHERE status = 'Pending' ORDER BY submitted_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                medicalList.add(mapResultSetToMedical(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalList;
    }

    public boolean removeMedical(int medicalId) {
        String query = "DELETE FROM Medical WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, medicalId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}