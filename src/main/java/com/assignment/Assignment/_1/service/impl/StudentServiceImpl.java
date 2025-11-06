package com.assignment.Assignment._1.service.impl;

import com.assignment.Assignment._1.dto.StudentRequestDto;
import com.assignment.Assignment._1.dto.StudentResponseDto;
import com.assignment.Assignment._1.entity.Student;
import com.assignment.Assignment._1.exception.ResourceNotFoundException;
import com.assignment.Assignment._1.exception.DuplicateResourceException;
import com.assignment.Assignment._1.repository.StudentRepository;
import com.assignment.Assignment._1.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {
        log.info("Creating student with email: {}", studentRequestDto.getEmail());

        if (studentRepository.existsByEmail(studentRequestDto.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentRequestDto.getEmail() + " already exists");
        }

        Student student = Student.builder()
                .name(studentRequestDto.getName())
                .email(studentRequestDto.getEmail())
                .course(studentRequestDto.getCourse())
                .age(studentRequestDto.getAge())
                .build();

        Student savedStudent = studentRepository.save(student);
        log.info("Student created successfully with ID: {}", savedStudent.getId());

        return mapToResponseDto(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        log.info("Fetching student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToResponseDto(student);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        log.info("Fetching all students with pagination: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return studentRepository.findAll(pageable)
                .map(this::mapToResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponseDto> searchStudents(String searchTerm, Pageable pageable) {
        log.info("Searching students with term: {}", searchTerm);
        return studentRepository.searchStudents(searchTerm, pageable)
                .map(this::mapToResponseDto);
    }

    @Override
    public StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto) {
        log.info("Updating student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Check if email is being updated and if the new email already exists
        if (!student.getEmail().equals(studentRequestDto.getEmail()) &&
            studentRepository.existsByEmail(studentRequestDto.getEmail())) {
            throw new DuplicateResourceException("Student with email " + studentRequestDto.getEmail() + " already exists");
        }

        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setCourse(studentRequestDto.getCourse());
        student.setAge(studentRequestDto.getAge());

        Student updatedStudent = studentRepository.save(student);
        log.info("Student updated successfully with ID: {}", updatedStudent.getId());

        return mapToResponseDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("Deleting student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
        log.info("Student deleted successfully with ID: {}", id);
    }

    private StudentResponseDto mapToResponseDto(Student student) {
        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .course(student.getCourse())
                .age(student.getAge())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}

