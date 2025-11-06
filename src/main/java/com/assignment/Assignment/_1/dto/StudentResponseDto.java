package com.assignment.Assignment._1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response DTO containing student information")
public class StudentResponseDto {

    @Schema(description = "Student ID", example = "1")
    private Long id;

    @Schema(description = "Student's full name", example = "John Doe")
    private String name;

    @Schema(description = "Student's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Student's course", example = "Computer Science")
    private String course;

    @Schema(description = "Student's age", example = "20")
    private Integer age;

    @Schema(description = "Timestamp when student was created")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when student was last updated")
    private LocalDateTime updatedAt;
}

