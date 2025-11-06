-- Student Management Database Initialization Script
-- Database: student_management

-- Create database (run this manually if database doesn't exist)
-- CREATE DATABASE student_management;

-- Connect to the database
-- \c student_management;

-- The students table will be created automatically by Hibernate
-- However, if you want to create it manually, use the following:

CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    course VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL CHECK (age >= 18),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_students_email ON students(email);
CREATE INDEX IF NOT EXISTS idx_students_name ON students(name);
CREATE INDEX IF NOT EXISTS idx_students_course ON students(course);

-- Sample data (optional - for testing)
INSERT INTO students (name, email, course, age, created_at, updated_at) VALUES
    ('John Doe', 'john.doe@example.com', 'Computer Science', 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Jane Smith', 'jane.smith@example.com', 'Mathematics', 22, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Bob Johnson', 'bob.johnson@example.com', 'Physics', 19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;

