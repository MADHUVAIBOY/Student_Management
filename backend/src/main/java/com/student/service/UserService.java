package com.student.service;

import com.student.model.User;
import com.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * UserService — contains business logic for user operations.
 * 
 * This service handles authentication (login check) without JWT.
 * It simply checks if the username and password match in the database.
 */
@Service
public class UserService {

    // Spring automatically injects the UserRepository bean
    @Autowired
    private UserRepository userRepository;

    /**
     * Authenticate a user by username and password.
     * 
     * Steps:
     * 1. Look up the user by username in the database
     * 2. If found, check if the password matches
     * 3. Return the User object if valid, or null if invalid
     * 
     * @param username the entered username
     * @param password the entered password
     * @return User object if login succeeds, null otherwise
     */
    public User login(String username, String password) {
        // Try to find the user by username
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // Check if user exists and password matches
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return user; // ✅ Login successful
            }
        }

        return null; // ❌ Login failed
    }
}
