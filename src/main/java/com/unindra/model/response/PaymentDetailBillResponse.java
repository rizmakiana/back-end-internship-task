package com.unindra.model.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetailBillResponse {

    private String paymentCategory;

    private String name;

    private BigDecimal unitPrice;
    
}