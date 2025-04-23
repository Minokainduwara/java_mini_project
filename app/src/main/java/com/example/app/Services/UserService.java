package com.example.app.Services;

import com.example.app.Models.User;
import com.example.app.Util.DatabaseUtil;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UserService {
    // Static field to store the currently logged-in user
    private static User currentUser = null;
    
    // Logger for standardized logging
    private static final Logger logger = new Logger(UserService.class.getSimpleName());
    
    // Hardcoded authentication credentials - no config file needed
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String LECTURER_USERNAME = "kamal";
    private static final String LECTURER_PASSWORD = "pass123";
    private static final String STUDENT_USERNAME = "ICT19001";
    private static final String STUDENT_PASSWORD = "pass123";
    private static final String TECHNICAL_OFFICER_USERNAME = "amara";
    private static final String TECHNICAL_OFFICER_PASSWORD = "pass123";
    private static final String DEFAULT_PROFILE_IMAGE_PATH = "/Images/default_profile.png";
    
    /**
     * Custom Logger class for standardized logging
     */
    private static class Logger {
        private final String className;
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        
        public enum LogLevel {
            INFO, DEBUG, ERROR, WARNING
        }
        
        public Logger(String className) {
            this.className = className;
        }
        
        public void info(String methodName, String message) {
            log(LogLevel.INFO, methodName, message);
        }
        
        public void debug(String methodName, String message) {
            log(LogLevel.DEBUG, methodName, message);
        }
        
        public void error(String methodName, String message) {
            log(LogLevel.ERROR, methodName, message);
        }
        
        public void error(String methodName, String message, Throwable throwable) {
            log(LogLevel.ERROR, methodName, message + " - " + throwable.getMessage());
        }
        
        public void warning(String methodName, String message) {
            log(LogLevel.WARNING, methodName, message);
        }
        
        private void log(LogLevel level, String methodName, String message) {
            String timestamp = LocalDateTime.now().format(formatter);
            System.out.println(String.format("%s | %-7s | %s | %s | %s", 
                timestamp, level, className, methodName, message));
        }
    }

    // Authentication method to validate username and password
    public User authenticate(String username, String password) {
        logger.info("authenticate", "Attempting authentication for user: " + username);
        try (Connection conn = DatabaseUtil.getConnection()) {
            if (conn != null) {
                // First try database authentication
                String query = "SELECT * FROM User WHERE username = ? AND password = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setString(1, username);
                    stmt.setString(2, password); // In a real app, use password hashing
                    
                    logger.debug("authenticate", "Executing DB query for authentication");
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        User user = mapResultSetToUser(rs);
                        currentUser = user;
                        logger.info("authenticate", "User authenticated successfully from database: " + user.getName());
                        return user;
                    }
                    logger.info("authenticate", "Database authentication failed for user: " + username);
                }
            } else {
                logger.warning("authenticate", "Database connection is null");
            }
        } catch (SQLException e) {
            logger.error("authenticate", "SQL Exception during authentication", e);
        }

        // Fallback demo authentication for testing when DB doesn't have users yet
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            logger.info("authenticate", "Fallback authentication successful for admin user");
            User user = createDemoUser(1, username, "Admin User", "admin@example.com", null, null, "Admin");
            currentUser = user;
            return user;
        } else if (username.equals(LECTURER_USERNAME) && password.equals(LECTURER_PASSWORD)) {
            logger.info("authenticate", "Fallback authentication successful for lecturer user");
            User user = createDemoUser(2, username, "Lecturer User", "lecturer@example.com", null, null, "Lecturer");
            currentUser = user;
            return user;
        } else if (username.equals(TECHNICAL_OFFICER_USERNAME) && password.equals(TECHNICAL_OFFICER_PASSWORD)) {
            logger.info("authenticate", "Fallback authentication successful for technical officer user");
            User user = createDemoUser(3, username, "Technical Officer", "officer@example.com", "1234567890", "Computer Science", "Technical Officer");
            currentUser = user;
            return user;
        } else if (username.equals(STUDENT_USERNAME) && password.equals(STUDENT_PASSWORD)) {
            logger.info("authenticate", "Fallback authentication successful for student user");
            User user = createDemoUser(4, username, "Student User", "student@example.com", null, null, "Student");
            currentUser = user;
            return user;
        }

        logger.warning("authenticate", "Authentication failed for user: " + username);
        return null; // Authentication failed
    }

    private User createDemoUser(int id, String username, String name, String email, String phone, String department, String role) {
        logger.debug("createDemoUser", "Creating demo user: " + name + " with role: " + role);
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        
        if (phone != null) {
            user.setPhone(phone);
        }
        
        if (department != null) {
            user.setDepartment(department);
        }
        
        // Set personalized profile picture based on user's name
        try {
            logger.debug("createDemoUser", "Creating personalized profile picture for: " + name);
            byte[] profilePic = createPersonalizedProfilePictureBytes(name);
            user.setProfilePicture(profilePic);
        } catch (Exception e) {
            logger.error("createDemoUser", "Error creating profile picture for user: " + name, e);
            // Fallback to default if personalized fails
            try {
                logger.debug("createDemoUser", "Falling back to default profile picture");
                user.setProfilePicture(getDefaultProfilePictureBytes());
            } catch (IOException ex) {
                logger.error("createDemoUser", "Error loading default profile picture", ex);
            }
        }
        
        logger.info("createDemoUser", "Demo user created successfully: " + name);
        return user;
    }
    
    private byte[] getDefaultProfilePictureBytes() throws IOException {
        logger.debug("getDefaultProfilePictureBytes", "Attempting to load default profile picture");
        // First try to load from resources using different methods to ensure it works
        InputStream is = null;
        
        // Method 1: Using ClassLoader
        is = getClass().getClassLoader().getResourceAsStream(DEFAULT_PROFILE_IMAGE_PATH.substring(1));
        
        // Method 2: Using Class directly if Method 1 fails
        if (is == null) {
            logger.debug("getDefaultProfilePictureBytes", "Method 1 failed, trying Method 2");
            is = getClass().getResourceAsStream(DEFAULT_PROFILE_IMAGE_PATH);
        }
        
        // Method 3: Try App class loader if previous methods fail
        if (is == null) {
            logger.debug("getDefaultProfilePictureBytes", "Method 2 failed, trying Method 3");
            is = com.example.app.App.class.getResourceAsStream(DEFAULT_PROFILE_IMAGE_PATH);
        }
        
        // Method 4: Try absolute path as last resort for resources
        if (is == null) {
            logger.debug("getDefaultProfilePictureBytes", "Method 3 failed, trying absolute paths");
            try {
                File file = new File("src/main/resources" + DEFAULT_PROFILE_IMAGE_PATH);
                if (file.exists()) {
                    is = new FileInputStream(file);
                    logger.info("getDefaultProfilePictureBytes", "Default profile picture loaded from file: " + file.getAbsolutePath());
                } else {
                    File alternativePath = new File("app/src/main/resources" + DEFAULT_PROFILE_IMAGE_PATH);
                    if (alternativePath.exists()) {
                        is = new FileInputStream(alternativePath);
                        logger.info("getDefaultProfilePictureBytes", "Default profile picture loaded from alternative path: " + alternativePath.getAbsolutePath());
                    } else {
                        logger.warning("getDefaultProfilePictureBytes", "Default profile picture not found at: " + file.getAbsolutePath() + " or " + alternativePath.getAbsolutePath());
                        return createDefaultImageBytes();
                    }
                }
            } catch (Exception e) {
                logger.error("getDefaultProfilePictureBytes", "Failed to load default profile from file", e);
                return createDefaultImageBytes();
            }
        }
        
        // Read the bytes and close the stream
        if (is != null) {
            try (InputStream stream = is) {
                logger.info("getDefaultProfilePictureBytes", "Loading default profile picture from stream");
                return stream.readAllBytes();
            }
        } else {
            logger.warning("getDefaultProfilePictureBytes", "All attempts to load default profile picture failed, creating default image");
            return createDefaultImageBytes();
        }
    }
    
    /**
     * Creates a simple colored circle as default profile picture if no image can be loaded
     */
    private byte[] createDefaultImageBytes() {
        logger.debug("createDefaultImageBytes", "Creating default image bytes");
        try {
            // Create a 200x200 image with a colored circle
            BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g = img.createGraphics();
            
            // Set rendering hints for better quality
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fill background with white
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0, 0, 200, 200);
            
            // Draw a colored circle (using system theme color - teal)
            g.setColor(new java.awt.Color(25, 124, 119)); // #197C77 - same as -fx-primary-color
            g.fillOval(20, 20, 160, 160);
            
            // Draw the first letter of "User" in white in the center
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
            java.awt.FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth("U");
            int textHeight = fm.getHeight();
            g.drawString("U", (200 - textWidth) / 2, ((200 - textHeight) / 2) + fm.getAscent());
            
            g.dispose();
            
            // Convert to PNG bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            logger.info("createDefaultImageBytes", "Default image bytes created successfully");
            return baos.toByteArray();
            
        } catch (Exception e) {
            logger.error("createDefaultImageBytes", "Failed to create default image", e);
            // Return an empty byte array as last resort
            return new byte[0];
        }
    }

    /**
     * Creates a personalized default profile picture with the first letter of the user's name
     * @param name The user's name
     * @return Byte array containing the image data
     */
    public byte[] createPersonalizedProfilePictureBytes(String name) {
        logger.debug("createPersonalizedProfilePictureBytes", "Creating personalized profile picture for: " + name);
        try {
            // Create a 200x200 image with a colored circle
            BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics2D g = img.createGraphics();
            
            // Set rendering hints for better quality
            g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fill background with transparent
            g.setComposite(java.awt.AlphaComposite.Clear);
            g.fillRect(0, 0, 200, 200);
            g.setComposite(java.awt.AlphaComposite.SrcOver);
            
            // Draw a colored circle - determine color based on name
            int hash = name.hashCode();
            // Generate color from hash but ensure it's not too dark or too light
            float hue = (Math.abs(hash) % 360) / 360.0f;
            java.awt.Color profileColor = java.awt.Color.getHSBColor(hue, 0.6f, 0.7f);
            g.setColor(profileColor);
            g.fillOval(0, 0, 200, 200);
            
            // Draw the first letter of the name in white in the center
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 100));
            java.awt.FontMetrics fm = g.getFontMetrics();
            
            String letter = name.isEmpty() ? "U" : name.substring(0, 1).toUpperCase();
            int textWidth = fm.stringWidth(letter);
            int textHeight = fm.getHeight();
            g.drawString(letter, (200 - textWidth) / 2, ((200 - textHeight) / 2) + fm.getAscent());
            
            g.dispose();
            
            // Convert to PNG bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            logger.info("createPersonalizedProfilePictureBytes", "Personalized profile picture created successfully for: " + name);
            return baos.toByteArray();
            
        } catch (Exception e) {
            logger.error("createPersonalizedProfilePictureBytes", "Failed to create personalized profile image for: " + name, e);
            try {
                // Fall back to default image
                logger.debug("createPersonalizedProfilePictureBytes", "Falling back to default profile picture");
                return getDefaultProfilePictureBytes();
            } catch (IOException ex) {
                logger.error("createPersonalizedProfilePictureBytes", "Failed to get default profile picture bytes", ex);
                return new byte[0];
            }
        }
    }

    /**
     * Gets a personalized profile picture for a user name as a JavaFX Image
     * @param name The user's name
     * @return A JavaFX Image containing the personalized profile picture
     */
    public Image getPersonalizedProfilePicture(String name) {
        logger.debug("getPersonalizedProfilePicture", "Getting personalized profile picture for: " + name);
        try {
            byte[] imageBytes = createPersonalizedProfilePictureBytes(name);
            if (imageBytes != null && imageBytes.length > 0) {
                logger.info("getPersonalizedProfilePicture", "Successfully created personalized profile picture for: " + name);
                return new Image(new ByteArrayInputStream(imageBytes));
            }
        } catch (Exception e) {
            logger.error("getPersonalizedProfilePicture", "Error creating personalized profile picture for: " + name, e);
        }
        
        // Fall back to default if personalized creation fails
        logger.warning("getPersonalizedProfilePicture", "Falling back to default profile picture for: " + name);
        return getDefaultProfilePicture();
    }

    public User getUserById(int userId) {
        logger.info("getUserById", "Fetching user with ID: " + userId);
        String query = "SELECT * FROM User WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                logger.info("getUserById", "Successfully fetched user: " + user.getName());
                return user;
            }
            logger.warning("getUserById", "No user found with ID: " + userId);
        } catch (SQLException e) {
            logger.error("getUserById", "SQL Exception while fetching user with ID: " + userId, e);
        }
        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        logger.debug("mapResultSetToUser", "Mapping ResultSet to User object");
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setDepartment(rs.getString("department"));
        user.setRole(rs.getString("role"));
        
        // Load profile picture if available
        try {
            Blob profilePictureBlob = rs.getBlob("profile_picture");
            if (profilePictureBlob != null && profilePictureBlob.length() > 0) {
                logger.debug("mapResultSetToUser", "Loading profile picture from database for user: " + user.getName());
                byte[] profilePictureBytes = profilePictureBlob.getBytes(1, (int) profilePictureBlob.length());
                user.setProfilePicture(profilePictureBytes);
            } else {
                // Create personalized profile picture based on user's name if none in DB
                logger.debug("mapResultSetToUser", "No profile picture in DB, creating personalized one for: " + user.getName());
                user.setProfilePicture(createPersonalizedProfilePictureBytes(user.getName()));
            }
        } catch (SQLException e) {
            logger.error("mapResultSetToUser", "Error loading profile picture for user: " + user.getName(), e);
            try {
                // Try to set personalized picture on error
                logger.debug("mapResultSetToUser", "Attempting to create personalized profile picture after error");
                user.setProfilePicture(createPersonalizedProfilePictureBytes(user.getName()));
            } catch (Exception ex) {
                // Fall back to default if personalized fails
                logger.warning("mapResultSetToUser", "Failed to create personalized picture, falling back to default");
                try {
                    user.setProfilePicture(getDefaultProfilePictureBytes());
                } catch (IOException ioEx) {
                    logger.error("mapResultSetToUser", "Failed to load default profile picture", ioEx);
                }
            }
        }
        
        logger.info("mapResultSetToUser", "Successfully mapped user: " + user.getName());
        return user;
    }

    public User getCurrentUser() {
        logger.debug("getCurrentUser", "Getting current user");
        if (currentUser != null) {
            logger.info("getCurrentUser", "Returning currently authenticated user: " + currentUser.getName());
            return currentUser;
        }

        // Fallback to demo user if not authenticated (for development only)
        logger.warning("getCurrentUser", "No authenticated user found, creating fallback demo user");
        User user = new User();
        try {
            user.setId(1);
            user.setName("John Doe");
            user.setUsername("technical_officer");
            user.setEmail("john.doe@university.edu");
            user.setPhone("1234567890");
            user.setDepartment("Computer Science");
            user.setRole("Technical Officer");
            // Use personalized profile picture based on name
            logger.debug("getCurrentUser", "Creating personalized profile picture for fallback user");
            user.setProfilePicture(createPersonalizedProfilePictureBytes(user.getName()));
            return user;
        } catch (Exception e) {
            logger.error("getCurrentUser", "Error creating profile picture for fallback user", e);
            try {
                user.setProfilePicture(getDefaultProfilePictureBytes());
            } catch (IOException ex) {
                logger.error("getCurrentUser", "Error loading default profile picture", ex);
            }
            return user;
        }
    }

    public void logout() {
        if (currentUser != null) {
            logger.info("logout", "Logging out user: " + currentUser.getName());
        } else {
            logger.warning("logout", "Logout called but no user was logged in");
        }
        // Clear the current user on logout
        currentUser = null;
    }

    public boolean updateUser(User user) {
        logger.info("updateUser", "Updating user: " + user.getName() + " (ID: " + user.getId() + ")");
        String query = "UPDATE User SET name = ?, email = ?, phone = ?, department = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getDepartment());
            stmt.setInt(5, user.getId());
            
            boolean success = stmt.executeUpdate() > 0;
            
            // Update the current user if it's the same user
            if (success && currentUser != null && currentUser.getId() == user.getId()) {
                logger.debug("updateUser", "Updating current user object with new details");
                currentUser.setName(user.getName());
                currentUser.setEmail(user.getEmail());
                currentUser.setPhone(user.getPhone());
                currentUser.setDepartment(user.getDepartment());
            }
            
            if (success) {
                logger.info("updateUser", "User updated successfully: " + user.getName());
            } else {
                logger.warning("updateUser", "Failed to update user: " + user.getName() + " - no rows affected");
            }
            
            return success;
        } catch (SQLException e) {
            logger.error("updateUser", "SQL Exception while updating user: " + user.getName(), e);
            return false;
        }
    }

    public boolean changePassword(int id, String newPassword) {
        logger.info("changePassword", "Changing password for user ID: " + id);
        String query = "UPDATE User SET password = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            boolean success = stmt.executeUpdate() > 0;
            
            if (success) {
                logger.info("changePassword", "Password changed successfully for user ID: " + id);
            } else {
                logger.warning("changePassword", "Failed to change password for user ID: " + id + " - no rows affected");
            }
            
            return success;
        } catch (SQLException e) {
            logger.error("changePassword", "SQL Exception while changing password for user ID: " + id, e);
            return false;
        }
    }
    
    public boolean updateProfilePicture(int userId, byte[] profilePictureData) {
        logger.info("updateProfilePicture", "Updating profile picture for user ID: " + userId);
        String query = "UPDATE User SET profile_picture = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            if (profilePictureData != null && profilePictureData.length > 0) {
                logger.debug("updateProfilePicture", "Setting new profile picture data, size: " + profilePictureData.length + " bytes");
                stmt.setBlob(1, new ByteArrayInputStream(profilePictureData), profilePictureData.length);
            } else {
                logger.warning("updateProfilePicture", "No profile picture data provided, setting to NULL");
                stmt.setNull(1, Types.BLOB);
            }
            
            stmt.setInt(2, userId);
            
            boolean success = stmt.executeUpdate() > 0;
            
            // Update current user if it's the same user
            if (success && currentUser != null && currentUser.getId() == userId) {
                logger.debug("updateProfilePicture", "Updating current user's profile picture in memory");
                currentUser.setProfilePicture(profilePictureData);
            }
            
            if (success) {
                logger.info("updateProfilePicture", "Profile picture updated successfully for user ID: " + userId);
            } else {
                logger.warning("updateProfilePicture", "Failed to update profile picture for user ID: " + userId + " - no rows affected");
            }
            
            return success;
        } catch (SQLException e) {
            logger.error("updateProfilePicture", "SQL Exception while updating profile picture for user ID: " + userId, e);
            return false;
        }
    }
    
    public Image getDefaultProfilePicture() {
        logger.debug("getDefaultProfilePicture", "Getting default profile picture");
        try {
            byte[] defaultImageBytes = getDefaultProfilePictureBytes();
            if (defaultImageBytes != null && defaultImageBytes.length > 0) {
                logger.info("getDefaultProfilePicture", "Default profile picture loaded successfully");
                return new Image(new ByteArrayInputStream(defaultImageBytes));
            }
        } catch (Exception e) {
            logger.error("getDefaultProfilePicture", "Error loading default profile picture", e);
        }
        
        // Last resort - create a very simple image object with a colored rectangle
        logger.warning("getDefaultProfilePicture", "Creating simple fallback image as last resort");
        return createSimpleFallbackImage();
    }
    
    private Image createSimpleFallbackImage() {
        logger.debug("createSimpleFallbackImage", "Creating simple fallback image");
        try {
            // Create a 1x1 pixel writeable image and fill with the system theme color
            int width = 200;
            int height = 200;
            WritableImage img = new WritableImage(width, height);
            
            // Fill with primary color
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    // Simulate a circle by checking distance from center
                    double distance = Math.sqrt(Math.pow(x - width/2.0, 2) + Math.pow(y - height/2.0, 2));
                    if (distance < width/2.0 - 5) {
                        // Inside the circle - fill with primary color
                        img.getPixelWriter().setColor(x, y, javafx.scene.paint.Color.valueOf("#197C77"));
                    } else {
                        // Outside the circle - transparent
                        img.getPixelWriter().setColor(x, y, javafx.scene.paint.Color.TRANSPARENT);
                    }
                }
            }
            logger.info("createSimpleFallbackImage", "Simple fallback image created successfully");
            return img;
        } catch (Exception e) {
            logger.error("createSimpleFallbackImage", "Failed to create fallback image", e);
            return null;
        }
    }
}