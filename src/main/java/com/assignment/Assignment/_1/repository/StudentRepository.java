package com.assignment.Assignment._1.repository;

import com.assignment.Assignment._1.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s WHERE " +
           "(:searchTerm IS NULL OR :searchTerm = '' OR " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.course) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Student> searchStudents(@Param("searchTerm") String searchTerm, Pageable pageable);
}

