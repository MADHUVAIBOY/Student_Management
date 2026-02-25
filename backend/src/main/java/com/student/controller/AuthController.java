package com.student.controller;

import com.student.model.User;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthController — handles login requests.
 * 
 * Endpoint:
 *   POST /api/auth/login — accept username & password, return user info
 * 
 * No JWT is used — this is a simple login check for beginners.
 * The frontend will store the user role in localStorage.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allow React frontend
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * POST /api/auth/login
     * 
     * Request Body (JSON):
     * {
     *   "username": "admin",
     *   "password": "admin123"
     * }
     * 
     * Success Response (200 OK):
     * {
     *   "message": "Login successful",
     *   "username": "admin",
     *   "role": "ADMIN"
     * }
     * 
     * Failure Response (401 Unauthorized):
     * {
     *   "message": "Invalid username or password"
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        // Attempt to authenticate
        User user = userService.login(username, password);

        Map<String, String> response = new HashMap<>();

        if (user != null) {
            // ✅ Login successful — return user info
            response.put("message", "Login successful");
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            // ❌ Login failed
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}
