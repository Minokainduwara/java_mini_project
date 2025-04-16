package com.example.app.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection databaseLink;

    public Connection getConnection() {
        //String databaseName = "LMS";
        String databaseUser = "root";  // Default XAMPP MySQL user
        String databasePassword = "";  // Default root password for XAMPP (if you haven't set one)
        String databaseUrl = "jdbc:mysql://localhost:3306/LMS";

        try {
            // Ensure the MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL Driver loaded successfully.");

            // Establish the connection
            databaseLink = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            System.out.println("Connection established successfully!");
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred while connecting to the database:");
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("SQL State: " + e.getSQLState());
            e.printStackTrace();  // Log the exception message and stack trace
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found:");
            e.printStackTrace();  // Log the exception message and stack trace
        } catch (Exception e) {
            System.out.println("An unexpected error occurred:");
            e.printStackTrace();  // Log any other unexpected exceptions
        }

        return databaseLink;
    }

    // Method to close the connection
    public void closeConnection() {
        if (databaseLink != null) {
            try {
                databaseLink.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.out.println("Error while closing the connection:");
                e.printStackTrace();
            }
        }
    }
}
