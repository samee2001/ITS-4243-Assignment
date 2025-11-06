package com.assignment.Assignment._1.service;

import com.assignment.Assignment._1.dto.StudentRequestDto;
import com.assignment.Assignment._1.dto.StudentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentResponseDto createStudent(StudentRequestDto studentRequestDto);

    StudentResponseDto getStudentById(Long id);

    Page<StudentResponseDto> getAllStudents(Pageable pageable);

    Page<StudentResponseDto> searchStudents(String searchTerm, Pageable pageable);

    StudentResponseDto updateStudent(Long id, StudentRequestDto studentRequestDto);

    void deleteStudent(Long id);
}

