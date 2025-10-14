package com.unindra.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DistrictResponse {

    private String id;

    private String name;
    
}