package com.nitaxtechnologies.BankApplication.model.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class AccountWithdrawalDTO {
    @NotBlank
    private String accountNumber;
    @NotBlank
    private String accountPassword;
    @Min(1)
    private Double withdrawnAmount;
}
