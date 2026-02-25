-- ============================================================
-- Student Management System - Database Setup Script
-- Run this in MySQL Workbench or MySQL CLI
-- ============================================================

-- Step 1: Create the database
CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- ============================================================
-- NOTE: The 'users' and 'students' tables will be AUTO-CREATED
-- by Spring Boot JPA when you run the backend for the first time.
-- Just run the INSERT statements AFTER starting the backend once.
-- ============================================================

-- Step 2: Insert default users (run AFTER backend has started once)
-- The tables will exist after first run.

-- Default ADMIN user
INSERT INTO users (username, password, role)
VALUES ('admin', 'admin123', 'ADMIN')
ON DUPLICATE KEY UPDATE role = 'ADMIN';

-- Default USER
INSERT INTO users (username, password, role)
VALUES ('user1', 'user123', 'USER')
ON DUPLICATE KEY UPDATE role = 'USER';

-- Step 3: (Optional) Insert sample student data for testing
INSERT INTO students (name, email, course, department)
VALUES
  ('Alice Johnson', 'alice@college.edu', 'B.Tech', 'Computer Science'),
  ('Bob Smith', 'bob@college.edu', 'MCA', 'Information Technology'),
  ('Carol Williams', 'carol@college.edu', 'B.Sc', 'Mathematics'),
  ('David Brown', 'david@college.edu', 'M.Tech', 'Electronics'),
  ('Emma Davis', 'emma@college.edu', 'BCA', 'Computer Science')
ON DUPLICATE KEY UPDATE name = VALUES(name);

-- ============================================================
-- Verify your data
-- ============================================================
SELECT 'Users:' AS info;
SELECT id, username, role FROM users;

SELECT 'Students:' AS info;
SELECT * FROM students;
