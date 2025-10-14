package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, String>{

    
}