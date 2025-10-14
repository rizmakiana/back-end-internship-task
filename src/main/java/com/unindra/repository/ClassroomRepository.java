package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Classroom;
import com.unindra.entity.Department;


@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String>{

    boolean existsByDepartmentAndGradeLevel(Department department, String gradeLevel);
    
}
