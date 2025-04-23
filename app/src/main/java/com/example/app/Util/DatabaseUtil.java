package com.example.app.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Simple database utility class with hardcoded credentials
 * Team members only need to change the credentials in this class
 */
public class DatabaseUtil {
    // Hardcoded database connection properties
    private static final String URL = "jdbc:mysql://localhost:3306/faculty_db";
    private static final String USER = "root";
    private static final String PASSWORD = "2003";
    
    
    private static Connection connection;
    private static boolean loggingEnabled = true; // Flag to control logging
    
    /**
     * Enable or disable connection logging
     * 
     * @param enabled true to enable logging, false to disable
     */
    public static void setLoggingEnabled(boolean enabled) {
        loggingEnabled = enabled;
    }

    /**
     * Get a database connection using the hardcoded credentials
     * 
     * @return A database connection
     * @throws SQLException if a database error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the MySQL driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                if (loggingEnabled) {
                    System.out.println("MySQL Driver loaded successfully.");
                }
                
                // Create the connection
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                if (loggingEnabled) {
                    System.out.println("Connection established successfully to faculty_db!");
                    // Disable logging after first successful connection
                    loggingEnabled = false;
                }
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found:");
                e.printStackTrace();
                throw new SQLException("Database driver not found", e);
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }

    /**
     * Close the database connection if open
     * 
     * @throws SQLException if a database error occurs
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            if (loggingEnabled) {
                System.out.println("Database connection closed successfully.");
            }
            // Re-enable logging for next connection
            loggingEnabled = true;
        }
    }
}