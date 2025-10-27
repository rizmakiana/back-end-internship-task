package com.unindra.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class PaymentItemDetail {

    private String studentId;

    private String studentName;

    private List<PaymentDetailBillResponse> payments;

}