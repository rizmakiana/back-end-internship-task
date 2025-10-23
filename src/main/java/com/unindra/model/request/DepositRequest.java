package com.unindra.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepositRequest {

    @NotBlank
    private String date;

    @NotNull
    private Integer month;

    @NotBlank
    private String year;

    @NotBlank
    private String clock;

    @NotBlank
    private String minute;

    @NotBlank
    private String referenceNo;

    @NotBlank
    private String amount;

}