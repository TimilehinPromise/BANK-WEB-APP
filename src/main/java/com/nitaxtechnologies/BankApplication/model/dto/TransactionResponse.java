package com.nitaxtechnologies.BankApplication.model.dto;

import com.nitaxtechnologies.BankApplication.model.TransactionType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    LocalDateTime transactionDate;
    TransactionType transactionType;
    String narration;
    Double amount;
    Double accountBalance;

    public TransactionResponse(LocalDateTime transactionDate, TransactionType transactionType, String narration, Double amount, Double accountBalance) {
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.narration = narration;
        this.amount = amount;
        this.accountBalance = accountBalance;
    }

    public TransactionResponse() {
    }
}
