package com.unindra.model.response;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PaymentRequest {

    @NotBlank
    private String referenceNumber;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private List<PaymentDetailBillResponse> payments;

}