package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String>{

    boolean existsByReferenceNumber(String referenceNumber);

}
