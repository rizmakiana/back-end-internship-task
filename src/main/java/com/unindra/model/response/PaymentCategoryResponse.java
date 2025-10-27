package com.unindra.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCategoryResponse {
 
    private String name;

    private Integer totalPayment;

    private List<PaymentDetailResponse> detailResponses;

}