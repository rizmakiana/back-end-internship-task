package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.PaymentItem;

@Repository
public interface PaymentItemRepository extends JpaRepository<PaymentItem, String> {
    
    

}