package com.unindra.model.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDepositsHistory {
    
    private String referenceNo;

    private String date;

    private BigDecimal depositAmount;

    private BigDecimal withdrawAmount;

}