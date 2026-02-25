package com.student.model;

import jakarta.persistence.*;

/**
 * Student Entity — represents a student record in the system.
 * 
 * Fields:
 *  - id         : auto-generated primary key
 *  - name       : full name of the student
 *  - email      : student's email address
 *  - course     : e.g., "B.Tech", "MCA", "BCA"
 *  - department : e.g., "Computer Science", "Mechanical"
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String department;

    // ─── Constructors ──────────────────────────────────────────────────────────

    public Student() {}

    public Student(String name, String email, String course, String department) {
        this.name = name;
        this.email = email;
        this.course = course;
        this.department = department;
    }

    // ─── Getters & Setters ─────────────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + 
               "', course='" + course + "', department='" + department + "'}";
    }
}
