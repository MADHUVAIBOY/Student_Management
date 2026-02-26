package com.student.controller;

import com.student.model.User;
import com.student.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * UserController — handles user management APIs (ADMIN only).
 *
 * Endpoints:
 * GET /api/users — list all users
 * POST /api/users — create new user
 * DELETE /api/users/{id} — delete a user
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ─── GET ALL USERS ─────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        // Clear passwords before sending to frontend
        users.forEach(u -> u.setPassword(""));
        return ResponseEntity.ok(users);
    }

    // ─── CREATE USER ───────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String username = body.get("username");
        String password = body.get("password");
        String role = body.get("role");

        // Validation
        if (username == null || username.isBlank()) {
            response.put("message", "Username is required.");
            return ResponseEntity.badRequest().body(response);
        }
        if (password == null || password.length() < 4) {
            response.put("message", "Password must be at least 4 characters.");
            return ResponseEntity.badRequest().body(response);
        }
        if (!List.of("ADMIN", "USER").contains(role)) {
            response.put("message", "Role must be ADMIN or USER.");
            return ResponseEntity.badRequest().body(response);
        }

        // Check username uniqueness
        if (userRepository.findByUsername(username).isPresent()) {
            response.put("message", "Username '" + username + "' is already taken.");
            return ResponseEntity.status(409).body(response);
        }

        // Save new user
        User newUser = new User(username, password, role);
        User saved = userRepository.save(newUser);

        response.put("message", "User created successfully!");
        response.put("id", saved.getId());
        response.put("username", saved.getUsername());
        response.put("role", saved.getRole());
        return ResponseEntity.status(201).body(response);
    }

    // ─── DELETE USER ───────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            userRepository.deleteById(id);
            response.put("message", "User deleted successfully.");
            return ResponseEntity.ok(response);
        }
        response.put("message", "User not found with id: " + id);
        return ResponseEntity.status(404).body(response);
    }
}
