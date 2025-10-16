package com.unindra.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDetailUpdate {
    
    @NotBlank
    private String name;

    @NotBlank
    private String unitPrice;

}