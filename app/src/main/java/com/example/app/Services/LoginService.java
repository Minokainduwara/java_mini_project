package com.example.app.Services;

import com.example.app.Models.User;

public class LoginService {
    private final UserService userService;
    
    public LoginService() {
        this.userService = new UserService();
    }
    
    /**
     * Authenticate a user with the given credentials
     * @param username The username to authenticate
     * @param password The password to authenticate
     * @return The authenticated User or null if authentication fails
     */
    public User login(String username, String password) {
        // Delegate authentication to the UserService
        return userService.authenticate(username, password);
    }
    
    /**
     * Log out the current user
     */
    public void logout() {
        userService.logout();
    }
}
