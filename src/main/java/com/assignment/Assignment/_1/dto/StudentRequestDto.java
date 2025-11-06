package com.assignment.Assignment._1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for creating or updating a student")
public class StudentRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Student's full name", example = "John Doe", required = true)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Schema(description = "Student's email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Course is required")
    @Size(min = 2, max = 100, message = "Course must be between 2 and 100 characters")
    @Schema(description = "Student's course", example = "Computer Science", required = true)
    private String course;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be greater than or equal to 18")
    @Max(value = 120, message = "Age must be less than or equal to 120")
    @Schema(description = "Student's age (must be 18 or older)", example = "20", required = true)
    private Integer age;
}

