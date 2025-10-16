package com.unindra.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCategoryRequest {
    
    private String name;

}