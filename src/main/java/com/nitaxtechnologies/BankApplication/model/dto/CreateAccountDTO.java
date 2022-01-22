package com.nitaxtechnologies.BankApplication.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class CreateAccountDTO {

    @NotEmpty
    @Length(min = 5, max = 50)
    private String accountName;

    @NotEmpty
    @Length(min = 10)
    private String accountPassword;

    @Min(500)
    private Double initialDeposit;

}
