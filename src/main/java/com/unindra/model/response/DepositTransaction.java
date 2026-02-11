package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositTransaction {

    private String date;

    private String reference;

    private String deposit;

    private String withdrawal;
    
}
