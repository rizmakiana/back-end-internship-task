package com.unindra.enitity;

import java.math.BigDecimal;

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
@Table(name = "payment_details")
public class PaymentDetail {
    
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "payment_category_id")
    private PaymentCategory paymentCategory;

}