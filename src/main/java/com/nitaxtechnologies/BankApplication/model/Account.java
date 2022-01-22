package com.nitaxtechnologies.BankApplication.model;

import lombok.Data;

@Data
public class Account {
    private String accountName;
    private String accountNumber;
    private Double balance;

    public Account() {
    }

    public Account(String accountName, String accountNumber, Double balance) {
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}
