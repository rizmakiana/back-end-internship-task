package com.unindra.model.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositReport {
    
    private String name;

    private String id;

    private String totalDeposit;

    private String totalWithdrawal;

    private String currentDeposit;

    private List<DepositTransaction> transactions;

}