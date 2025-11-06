package com.assignment.Assignment._1.controller;

import com.assignment.Assignment._1.dto.ApiResponse;
import com.assignment.Assignment._1.dto.StudentRequestDto;
import com.assignment.Assignment._1.dto.StudentResponseDto;
import com.assignment.Assignment._1.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student Management", description = "REST API for managing students")
public class StudentController {

    private final StudentService studentService;

    @Operation(
            summary = "Create a new student",
            description = "Creates a new student with the provided information. Email must be unique."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Student created successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Student with email already exists"
            )
    })
    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> createStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto) {
        log.info("POST /api/students - Creating student");
        StudentResponseDto student = studentService.createStudent(studentRequestDto);
        ApiResponse<StudentResponseDto> response = ApiResponse.success(student, "Student created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all students",
            description = "Retrieves all students with pagination and sorting support. Supports search by name or course."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Students retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<ApiResponse<Page<StudentResponseDto>>> getAllStudents(
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field (name, email, course, age, createdAt, updatedAt)", example = "name")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc, desc)", example = "asc")
            @RequestParam(defaultValue = "asc") String sortDir,
            @Parameter(description = "Search term for name or course", example = "John")
            @RequestParam(required = false) String search) {
        
        log.info("GET /api/students - Fetching all students with page={}, size={}, sortBy={}, sortDir={}, search={}",
                page, size, sortBy, sortDir, search);

        Sort sort = sortDir.equalsIgnoreCase("desc") 
                ? Sort.by(sortBy).descending() 
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<StudentResponseDto> students;
        if (search != null && !search.trim().isEmpty()) {
            students = studentService.searchStudents(search.trim(), pageable);
        } else {
            students = studentService.getAllStudents(pageable);
        }

        ApiResponse<Page<StudentResponseDto>> response = ApiResponse.success(
                students, "Students retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get student by ID",
            description = "Retrieves a specific student by their ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> getStudentById(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("GET /api/students/{} - Fetching student", id);
        StudentResponseDto student = studentService.getStudentById(id);
        ApiResponse<StudentResponseDto> response = ApiResponse.success(student, "Student retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Update student",
            description = "Updates an existing student's information. Email must be unique."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student updated successfully",
                    content = @Content(schema = @Schema(implementation = StudentResponseDto.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "409",
                    description = "Student with email already exists"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDto>> updateStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDto studentRequestDto) {
        log.info("PUT /api/students/{} - Updating student", id);
        StudentResponseDto student = studentService.updateStudent(id, studentRequestDto);
        ApiResponse<StudentResponseDto> response = ApiResponse.success(student, "Student updated successfully");
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Delete student",
            description = "Deletes a student by their ID"
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Student deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Student not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteStudent(
            @Parameter(description = "Student ID", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/students/{} - Deleting student", id);
        studentService.deleteStudent(id);
        ApiResponse<Object> response = ApiResponse.success(null, "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
}

