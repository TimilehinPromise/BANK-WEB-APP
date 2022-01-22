package com.nitaxtechnologies.BankApplication.entities;

import com.nitaxtechnologies.BankApplication.model.TransactionType;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class Transactions {
    LocalDateTime transactionDate;
    TransactionType transactionType;
    String narration;
    Double amount;
    Double accountBalance;
    String accountNumber;

    public Transactions(LocalDateTime transactionDate, TransactionType transactionType, String narration, Double amount, Double accountBalance, String accountNumber) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.accountBalance = accountBalance;
        this.accountNumber = accountNumber;
    }
}
