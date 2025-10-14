package com.unindra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Regency;

@Repository
public interface RegencyRepository extends JpaRepository<Regency, String>{
    
    List<Regency> findByProvinceId(String provinceId);
    
}