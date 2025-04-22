package com.example.app.Models;

import com.example.app.Models.DatabaseConnection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserModel {

    public InputStream getProfilePictureByUsername(String username) {
        String query = "SELECT profile_picture FROM User WHERE username = ?";

        try (
                Connection conn = DatabaseConnection.getConnection(); // Ensures you have a valid connection
                PreparedStatement ps = conn.prepareStatement(query)
        ) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBinaryStream("profile_picture"); // Returns the image as InputStream
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // If no image is found, return null
    }
}
