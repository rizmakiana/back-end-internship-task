package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unindra.entity.Deposit;

import java.math.BigDecimal;
import java.util.Optional;

import com.unindra.model.util.TransactionType;
import com.unindra.entity.Student;


@Repository
public interface DepositRepository extends JpaRepository<Deposit, String> {
    
    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Deposit d WHERE d.student = :student AND d.transactionType = :type")
    Optional<BigDecimal> sumByStudentAndTransactionType(@Param("student") Student student, @Param("type") TransactionType type);


}
