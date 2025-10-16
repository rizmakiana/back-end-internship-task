package com.unindra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unindra.entity.PaymentDetail;
import com.unindra.entity.PaymentCategory;
import com.unindra.entity.Classroom;



@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, String>{
    
    boolean existsByPaymentCategoryAndClassroomAndName(PaymentCategory paymentCategory, Classroom classroom, String name);

}
