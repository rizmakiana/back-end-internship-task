package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Section;
import java.util.Optional;
import com.unindra.entity.Classroom;

@Repository
public interface SectionRepository extends JpaRepository<Section, String>{

    Optional<Section> findByCode(String code);

    Optional<Section> findByClassroomAndCode(Classroom classroom, String code);

    boolean existsByClassroomAndName(Classroom classroom, Character name);
    
}