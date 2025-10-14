package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Classroom;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, String>{
    
}
