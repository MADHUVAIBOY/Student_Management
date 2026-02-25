package com.student.model;

import jakarta.persistence.*;

/**
 * User Entity — represents a user in the system.
 * 
 * Fields:
 *  - id       : auto-generated primary key
 *  - username : unique login name
 *  - password : plain text password (simple auth for beginners)
 *  - role     : either "ADMIN" or "USER"
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Role: "ADMIN" or "USER"
    @Column(nullable = false)
    private String role;

    // ─── Constructors ──────────────────────────────────────────────────────────

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', role='" + role + "'}";
    }
}
