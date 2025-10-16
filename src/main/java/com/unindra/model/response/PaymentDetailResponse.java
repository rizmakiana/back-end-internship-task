package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetailResponse {
    
    private String categoryName;

    private String classroomCode;

    private String paymentName;

    private String unitPrice;

}