package com.student.controller;

import com.student.model.Student;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * StudentController — handles all student-related API requests.
 * 
 * Endpoints:
 *   GET    /api/students              — get all students
 *   GET    /api/students/search?name= — search by name
 *   GET    /api/students/count        — total count
 *   POST   /api/students              — add student (ADMIN)
 *   PUT    /api/students/{id}         — update student (ADMIN)
 *   DELETE /api/students/{id}         — delete student (ADMIN)
 * 
 * NOTE: Role-based access is enforced on the FRONTEND side (simple implementation).
 *       In a real app, you'd secure this with Spring Security + JWT.
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:5173") // Allow React frontend
public class StudentController {

    @Autowired
    private StudentService studentService;

    // ─── GET ALL STUDENTS ──────────────────────────────────────────────────────

    /**
     * GET /api/students
     * Returns all students in the database.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // ─── SEARCH BY NAME ────────────────────────────────────────────────────────

    /**
     * GET /api/students/search?name=Alice
     * Returns students whose name contains the search keyword (case-insensitive).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudents(@RequestParam String name) {
        List<Student> results = studentService.searchByName(name);
        return ResponseEntity.ok(results);
    }

    // ─── GET TOTAL COUNT ───────────────────────────────────────────────────────

    /**
     * GET /api/students/count
     * Returns total number of students (useful for Dashboard).
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCount() {
        Map<String, Long> response = new HashMap<>();
        response.put("total", studentService.countStudents());
        return ResponseEntity.ok(response);
    }

    // ─── GET STUDENT BY ID ─────────────────────────────────────────────────────

    /**
     * GET /api/students/{id}
     * Returns a single student by their ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        Map<String, String> error = new HashMap<>();
        error.put("message", "Student not found with id: " + id);
        return ResponseEntity.status(404).body(error);
    }

    // ─── ADD STUDENT (ADMIN) ───────────────────────────────────────────────────

    /**
     * POST /api/students
     * 
     * Request Body (JSON):
     * {
     *   "name": "Alice",
     *   "email": "alice@example.com",
     *   "course": "B.Tech",
     *   "department": "Computer Science"
     * }
     * 
     * Returns: The saved student with generated ID
     */
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        try {
            Student saved = studentService.addStudent(student);
            return ResponseEntity.status(201).body(saved); // 201 Created
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to add student: " + e.getMessage());
            return ResponseEntity.status(400).body(error);
        }
    }

    // ─── UPDATE STUDENT (ADMIN) ────────────────────────────────────────────────

    /**
     * PUT /api/students/{id}
     * 
     * Request Body: Same as POST
     * Updates an existing student's information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        Student updated = studentService.updateStudent(id, student);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        Map<String, String> error = new HashMap<>();
        error.put("message", "Student not found with id: " + id);
        return ResponseEntity.status(404).body(error);
    }

    // ─── DELETE STUDENT (ADMIN) ────────────────────────────────────────────────

    /**
     * DELETE /api/students/{id}
     * 
     * Deletes a student by their ID.
     * Returns 200 OK if deleted, 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            response.put("message", "Student deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Student not found with id: " + id);
        return ResponseEntity.status(404).body(response);
    }
}
