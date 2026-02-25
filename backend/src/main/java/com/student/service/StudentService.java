package com.student.service;

import com.student.model.Student;
import com.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * StudentService — contains business logic for student CRUD operations.
 * 
 * This service layer sits between the Controller and Repository:
 *   Controller → Service → Repository → Database
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // ─── GET ALL STUDENTS ──────────────────────────────────────────────────────

    /**
     * Retrieve all students from the database.
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ─── GET STUDENT BY ID ─────────────────────────────────────────────────────

    /**
     * Retrieve a specific student by their ID.
     * @param id the student ID
     * @return Optional<Student> — present if found
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // ─── SEARCH BY NAME ────────────────────────────────────────────────────────

    /**
     * Search students by name (case-insensitive, partial match).
     * Example: searching "ali" will find "Alice", "Malik", etc.
     * 
     * @param name the search keyword
     * @return List of matching students
     */
    public List<Student> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }

    // ─── ADD STUDENT ───────────────────────────────────────────────────────────

    /**
     * Add a new student to the database.
     * @param student the student object to save
     * @return the saved student (with generated ID)
     */
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    // ─── UPDATE STUDENT ────────────────────────────────────────────────────────

    /**
     * Update an existing student's information.
     * 
     * Steps:
     * 1. Find the student by ID
     * 2. Update each field with new values
     * 3. Save back to database
     * 
     * @param id      the student ID to update
     * @param updated the new student data
     * @return updated Student, or null if not found
     */
    public Student updateStudent(Long id, Student updated) {
        Optional<Student> optional = studentRepository.findById(id);

        if (optional.isPresent()) {
            Student existing = optional.get();
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setCourse(updated.getCourse());
            existing.setDepartment(updated.getDepartment());
            return studentRepository.save(existing); // ✅ Save updated record
        }

        return null; // ❌ Student not found
    }

    // ─── DELETE STUDENT ────────────────────────────────────────────────────────

    /**
     * Delete a student by their ID.
     * @param id the student ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true; // ✅ Deleted successfully
        }
        return false; // ❌ Student not found
    }

    // ─── COUNT STUDENTS ────────────────────────────────────────────────────────

    /**
     * Get the total number of students in the database.
     * @return total student count
     */
    public long countStudents() {
        return studentRepository.count();
    }
}
