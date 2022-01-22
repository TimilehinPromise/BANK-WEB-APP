package com.nitaxtechnologies.BankApplication.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositDTO {
    @NotEmpty
    private String accountNumber;
    @Min(1)
    @Max(1000000)
    private Double amount;



}
