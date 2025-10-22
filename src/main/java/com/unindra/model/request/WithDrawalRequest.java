package com.unindra.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithDrawalRequest {
    
    private String studentId;

    private String date;

    private Integer month;

    private String year;

    private String referenceNo;

    private String amount;

}