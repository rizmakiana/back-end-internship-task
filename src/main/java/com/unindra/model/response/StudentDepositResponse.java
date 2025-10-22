package com.unindra.model.response;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDepositResponse {
    
    private String studentId;

    private String studentName;

    private BigDecimal totalDeposit;

}