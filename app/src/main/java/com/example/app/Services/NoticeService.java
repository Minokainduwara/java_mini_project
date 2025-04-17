package com.example.app.Services;

import com.example.app.Models.Notice;
import com.example.app.Util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeService {
    public List<Notice> getNoticesForTechnicalOfficers(String department) {
        List<Notice> notices = new ArrayList<>();
        String query = "SELECT * FROM Notice WHERE " +
                "(target_roles LIKE ? OR target_roles LIKE ? OR target_roles LIKE ? OR target_roles IS NULL) AND " +
                "(target_departments LIKE ? OR target_departments IS NULL) " +
                "ORDER BY post_date DESC";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%Technical Officer%");
            stmt.setString(2, "%Technical Officer,%");
            stmt.setString(3, "%,Technical Officer%");
            stmt.setString(4, "%" + department + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notices.add(mapResultSetToNotice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notices;
    }

    public Notice getNoticeById(int noticeId) {
        String query = "SELECT * FROM Notice WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, noticeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToNotice(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Notice mapResultSetToNotice(ResultSet rs) throws SQLException {
        Notice notice = new Notice();
        notice.setId(rs.getInt("id"));
        notice.setTitle(rs.getString("title"));
        notice.setContent(rs.getString("content"));
        notice.setPostedBy(rs.getInt("posted_by"));

        if (rs.getTimestamp("post_date") != null) {
            notice.setPostDate(rs.getTimestamp("post_date").toLocalDateTime());
        }

        notice.setTargetRoles(rs.getString("target_roles"));
        notice.setTargetDepartments(rs.getString("target_departments"));

        return notice;
    }
}