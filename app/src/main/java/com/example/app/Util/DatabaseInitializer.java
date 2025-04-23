package com.example.app.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for database initialization and sample data loading
 */
public class DatabaseInitializer {

    // Hardcoded SQL script path - no more config file dependency
    private static final String SQL_SCRIPT_PATH = "/faculty_db.sql";

    /**
     * Initializes the database with tables and sample data
     * @return true if initialization was successful, false otherwise
     */
    public static boolean initializeDatabase() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            System.out.println("Executing SQL script from: " + SQL_SCRIPT_PATH);
            executeSqlScript(connection, SQL_SCRIPT_PATH);
            System.out.println("Database initialized successfully!");
            return true;
        } catch (SQLException | IOException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Executes an SQL script from a resource file
     * @param connection Database connection
     * @param resourcePath Path to the SQL script resource
     * @throws IOException If the resource cannot be read
     * @throws SQLException If there's an error executing SQL statements
     */
    private static void executeSqlScript(Connection connection, String resourcePath)
            throws IOException, SQLException {

        // Read SQL file from resources
        InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IOException("Could not find resource: " + resourcePath);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            executeSqlStatements(connection, parseStatements(reader));
        }
    }

    /**
     * Parses SQL statements from a reader
     * @param reader Reader containing SQL statements
     * @return List of SQL statements
     * @throws IOException If there's an error reading from the reader
     */
    private static List<String> parseStatements(BufferedReader reader) throws IOException {
        List<String> statements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            // Skip comments and empty lines
            if (line.trim().isEmpty() || line.trim().startsWith("--")) {
                continue;
            }

            currentStatement.append(line).append(" ");

            // If the line contains a semicolon, it's the end of a statement
            if (line.trim().endsWith(";")) {
                statements.add(currentStatement.toString());
                currentStatement = new StringBuilder();
            }
        }

        // Add the last statement if there is one
        if (currentStatement.length() > 0) {
            statements.add(currentStatement.toString());
        }

        return statements;
    }

    /**
     * Executes a list of SQL statements
     * @param connection Database connection
     * @param statements List of SQL statements to execute
     * @throws SQLException If there's an error executing SQL statements
     */
    private static void executeSqlStatements(Connection connection, List<String> statements)
            throws SQLException {

        boolean autoCommit = connection.getAutoCommit();
        try {
            connection.setAutoCommit(false);
            try (Statement stmt = connection.createStatement()) {
                for (String sql : statements) {
                    stmt.execute(sql);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    /**
     * Checks if the database is already initialized by looking for essential tables
     * @return true if the database appears to be initialized, false otherwise
     */
    public static boolean isDatabaseInitialized() {
        try (Connection connection = DatabaseUtil.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // Check if User table exists and has at least one admin
                stmt.executeQuery("SELECT COUNT(*) FROM User WHERE role = 'Admin'");
                return true;
            }
        } catch (SQLException e) {
            // Table doesn't exist or other error
            return false;
        }
    }
}