package com.example.app.Models;

import java.time.LocalDateTime;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;

public class User {
    private int id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String role; // Enum: 'Admin', 'Lecturer', 'Technical Officer', 'Student'
    private String registrationNumber; // For students
    private LocalDateTime createdAt;
    private byte[] profilePicture; // Binary data for profile picture
    private transient Image profileImage; // JavaFX image for UI display (not stored in DB)

    // Constructors
    public User() {}

    public User(int id, String username, String name, String email, String phone,
                String department, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.role = role;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public byte[] getProfilePicture() { return profilePicture; }
    public void setProfilePicture(byte[] profilePicture) { 
        this.profilePicture = profilePicture;
        // Clear cached image when updating binary data
        this.profileImage = null;
    }
    
    // Get profile image for UI display
    public Image getProfileImage() {
        // Return cached image if available
        if (profileImage != null) {
            return profileImage;
        }
        
        // Create image from byte array if we have profile picture data
        if (profilePicture != null && profilePicture.length > 0) {
            try {
                profileImage = new Image(new ByteArrayInputStream(profilePicture));
                return profileImage;
            } catch (Exception e) {
                System.err.println("Error creating image from profile picture data: " + e.getMessage());
            }
        }
        
        // Return null if we can't create an image (will be handled by UI to show default)
        return null;
    }
    
    // Determine if the user has a custom profile picture
    public boolean hasProfilePicture() {
        return profilePicture != null && profilePicture.length > 0;
    }
}