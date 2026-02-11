package com.unindra.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentReport {

    private String id;
    
    private String name;
 
    private String birthPlace;
    
    private String birthDate;
    
}