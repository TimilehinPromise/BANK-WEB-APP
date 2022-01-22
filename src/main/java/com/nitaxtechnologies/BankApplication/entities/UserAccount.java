package com.nitaxtechnologies.BankApplication.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {

    private String accountName;
    private String accountPassword;
    private Double accountBalance;
    private String accountNumber;

}
