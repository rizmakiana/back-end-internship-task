package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.PaymentCategory;
import java.util.Optional;

@Repository
public interface PaymentCategoryRepository extends JpaRepository<PaymentCategory, String>{

    boolean existsByName(String name);

    Optional<PaymentCategory> findByName(String name);

    boolean existsByNameAndIdNot(String name, String id);
     
}
