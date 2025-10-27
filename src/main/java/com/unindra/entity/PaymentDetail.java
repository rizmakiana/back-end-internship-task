package com.unindra.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(precision = 12, scale = 0)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "payment_category_id")
    private PaymentCategory paymentCategory;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = true)
    private Classroom classroom;

    @OneToMany(mappedBy = "paymentDetail")
    private List<PaymentItem> paymentItems;

}