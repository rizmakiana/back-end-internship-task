package com.unindra.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClassroomReport {
    
    private String code;
    
    private String department;
    
    private String grade;
    
    private String section;
    
}
