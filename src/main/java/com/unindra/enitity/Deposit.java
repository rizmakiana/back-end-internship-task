package com.unindra.enitity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.unindra.model.util.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "deposits")
public class Deposit {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String referenceNumber;

    private LocalDateTime date;

    private BigDecimal amount;

    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

}