package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentBillResponse {
    
    private String studentId;

    private String name;

    private String bills;

}