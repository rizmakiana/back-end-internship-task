package com.unindra.model.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String token;

    private Date expiredAt;
    
}