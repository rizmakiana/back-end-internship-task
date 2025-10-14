package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>{

    boolean existsByName(String name);

    boolean existsByCode(String code);
    
}