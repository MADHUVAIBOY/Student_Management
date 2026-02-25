package com.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Student Management System Spring Boot Application.
 * 
 * @SpringBootApplication enables:
 *   - @Configuration (bean definitions)
 *   - @EnableAutoConfiguration (auto-configures Spring)
 *   - @ComponentScan (scans for components in this package)
 */
@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("\n✅ Student Management System is running!");
        System.out.println("📌 Backend URL: http://localhost:8080");
    }
}
