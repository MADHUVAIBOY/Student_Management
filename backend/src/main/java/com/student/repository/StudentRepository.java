package com.student.repository;

import com.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StudentRepository — handles database operations for the Student entity.
 * 
 * JpaRepository provides:
 *  - findAll(), findById(), save(), deleteById(), count(), etc.
 * 
 * Custom methods for search functionality.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Search students by name (case-insensitive, partial match).
     * 
     * SQL equivalent: WHERE LOWER(name) LIKE LOWER('%keyword%')
     * 
     * @param name the name keyword to search
     * @return list of matching students
     */
    List<Student> findByNameContainingIgnoreCase(String name);
}
