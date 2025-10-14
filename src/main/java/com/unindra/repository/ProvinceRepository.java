package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    
}