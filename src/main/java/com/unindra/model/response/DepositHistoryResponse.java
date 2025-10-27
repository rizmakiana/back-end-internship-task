package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositHistoryResponse {

    private String referenceNumber;

    private String studentName;

    private String date;

    private String depositAmount;

    private String withdrawalAmount;
    
}