package com.student.repository;

import com.student.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository — handles database operations for the User entity.
 * 
 * JpaRepository provides built-in methods:
 *  - findAll(), findById(), save(), deleteById(), etc.
 * 
 * We add custom method: findByUsername for login lookup.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username.
     * Spring Data JPA automatically generates the SQL query based on the method name.
     * 
     * @param username the username to search
     * @return Optional<User> — present if found, empty if not
     */
    Optional<User> findByUsername(String username);
}
