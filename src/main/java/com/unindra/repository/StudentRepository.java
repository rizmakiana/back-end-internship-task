package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Student;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String>{

    Optional<Student> findByStudentId(String studentId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT MAX(CAST(student_id AS UNSIGNED)) FROM students", nativeQuery = true)
    Optional<String> findMaxStudentId();

    boolean existsByUsernameAndIdNot(String username, String id);
    boolean existsByEmailAndIdNot(String email, String id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, String id);

}