package com.unindra.model.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PaymentHistoryResponse {

    private String referenceNumber;

    private String studentName;

    private String date;

    private BigDecimal totalAmount;

}