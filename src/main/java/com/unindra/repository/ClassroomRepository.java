package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String>{

    boolean existsByDepartmentAndGradeLevel(Department department, String gradeLevel);

    Optional<Classroom> findByCode(String code);

    Optional<Classroom> findByDepartmentAndGradeLevel(Department department, String gradeLevel);
    
}
