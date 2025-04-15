package com.example.app.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    // Database connection properties
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    
    // Config file paths to try (in order)
    private static final String[] CONFIG_PATHS = {
        "/config/config.properties",
        "/config.properties",
        "config/config.properties",
        "config.properties"
    };
    
    // Default database values
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/faculty_db";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASSWORD = "2003";
    
    // Load properties from config file
    static {
        createFreshConfigFile();
        loadProperties();
    }
    
    /**
     * Creates a fresh config file with correct encoding
     */
    private static void createFreshConfigFile() {
        try {
            // Create a new properties object with all required properties
            Properties newProps = new Properties();
            
            // Database configuration
            newProps.setProperty("db.url", DEFAULT_URL);
            newProps.setProperty("db.user", DEFAULT_USER);
            newProps.setProperty("db.password", DEFAULT_PASSWORD);
            
            // Application settings
            newProps.setProperty("app.name", "Faculty Management System");
            newProps.setProperty("app.version", "1.0");
            newProps.setProperty("app.environment", "development");
            
            // Fallback authentication credentials
            newProps.setProperty("auth.fallback.admin.username", "admin");
            newProps.setProperty("auth.fallback.admin.password", "admin");
            newProps.setProperty("auth.fallback.lecturer.username", "lecturer");
            newProps.setProperty("auth.fallback.lecturer.password", "lecturer");
            newProps.setProperty("auth.fallback.student.username", "student");
            newProps.setProperty("auth.fallback.student.password", "student");
            newProps.setProperty("auth.fallback.technicalOfficer.username", "technical");
            newProps.setProperty("auth.fallback.technicalOfficer.password", "technical");
            
            // Resource paths
            newProps.setProperty("path.sql.init", "/faculty_db.sql");
            newProps.setProperty("path.image.default_profile", "/Images/default_profile.png");
            
            // Logging configuration
            newProps.setProperty("log.level", "INFO");
            newProps.setProperty("log.file", "app.log");
            
            // Try to save to various locations
            boolean saved = false;
            
            // First try to save to the target directory
            try {
                // Create a temporary clean config file in memory
                String configPath = System.getProperty("user.dir") + "/app/target/classes/config/config.properties";
                File configDir = new File(System.getProperty("user.dir") + "/app/target/classes/config");
                if (!configDir.exists()) {
                    configDir.mkdirs();
                }
                
                try (OutputStream out = new FileOutputStream(configPath)) {
                    newProps.store(out, "Database Configuration");
                    saved = true;
                    System.out.println("Created fresh config file at: " + configPath);
                }
            } catch (Exception e) {
                System.err.println("Failed to save config to target directory: " + e.getMessage());
            }
            
            // Then try to save to the source directory
            if (!saved) {
                try {
                    String configPath = System.getProperty("user.dir") + "/app/src/main/resources/config/config.properties";
                    try (OutputStream out = new FileOutputStream(configPath)) {
                        newProps.store(out, "Database Configuration");
                        System.out.println("Created fresh config file at: " + configPath);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to save config to src directory: " + e.getMessage());
                }
            }
            
        } catch (Exception e) {
            System.err.println("Failed to create fresh config file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void loadProperties() {
        Properties properties = new Properties();
        boolean loaded = false;
        
        // Try loading from classpath using different paths
        for (String configPath : CONFIG_PATHS) {
            try (InputStream inputStream = DatabaseUtil.class.getResourceAsStream(configPath)) {
                if (inputStream != null) {
                    System.out.println("Found config file at: " + configPath);
                    properties.load(inputStream);
                    loaded = true;
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error reading config file from " + configPath + ": " + e.getMessage());
            }
        }
        
        // If not found in classpath, try file system paths
        if (!loaded) {
            String[] fileSystemPaths = {
                "src/main/resources/config/config.properties",
                "app/src/main/resources/config/config.properties",
                "app/target/classes/config/config.properties"
            };
            
            for (String fsPath : fileSystemPaths) {
                File configFile = new File(fsPath);
                if (configFile.exists()) {
                    try (FileInputStream fis = new FileInputStream(configFile)) {
                        System.out.println("Found config file at file system path: " + configFile.getAbsolutePath());
                        properties.load(fis);
                        loaded = true;
                        break;
                    } catch (IOException e) {
                        System.err.println("Error reading config file from " + fsPath + ": " + e.getMessage());
                    }
                }
            }
        }
        
        // Debug output of all loaded properties
        if (loaded) {
            System.out.println("Configuration file loaded. Available properties:");
            for (String propName : properties.stringPropertyNames()) {
                // Don't print password values for security
                if (propName.contains("password")) {
                    System.out.println(" - " + propName + "=********");
                } else {
                    System.out.println(" - " + propName + "=" + properties.getProperty(propName));
                }
            }
        } else {
            System.err.println("No configuration file found in any location. Using default values.");
        }
                
        // Get properties with validation
        URL = properties.getProperty("db.url");
        USER = properties.getProperty("db.user");
        PASSWORD = properties.getProperty("db.password");
        
        // Validate essential properties
        boolean isValid = true;
        if (URL == null || URL.trim().isEmpty()) {
            System.err.println("Database URL not found in properties file, using default");
            URL = DEFAULT_URL;
            isValid = false;
        }
        
        if (USER == null || USER.trim().isEmpty()) {
            System.err.println("Database username not found in properties file, using default");
            USER = DEFAULT_USER;
            isValid = false;
        }
        
        if (PASSWORD == null) {
            System.err.println("Database password not found in properties file, using default");
            PASSWORD = DEFAULT_PASSWORD;
            isValid = false;
        }
        
        if (isValid) {
            System.out.println("Database properties loaded successfully");
        } else {
            System.out.println("Database properties loaded with some defaults");
        }
        
        // Final validation check before proceeding
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("Database URL cannot be null or empty");
        }
        
        // Debug output to help diagnose issues
        System.out.println("Using database URL: " + URL);
        System.out.println("Using database username: " + USER);
    }

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (URL == null) {
            throw new SQLException("Database URL is null. Cannot establish connection.");
        }
        
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("Failed to connect to database: " + e.getMessage());
                throw e;
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    
    // Method to reload properties if needed
    public static void reloadProperties() {
        loadProperties();
    }
}