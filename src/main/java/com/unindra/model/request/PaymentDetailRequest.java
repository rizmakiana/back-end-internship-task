package com.unindra.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetailRequest {
    
    private String categoryName;

    private String classroomCode;

    private String unitPrice;

}