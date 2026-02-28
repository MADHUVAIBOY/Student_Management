package com.student.controller;

import com.student.model.User;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") 
public class AuthController {

    @Autowired
    private UserService userService;

    
    
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
