package com.nitaxtechnologies.BankApplication.model;

import com.nitaxtechnologies.BankApplication.model.Account;
import lombok.Data;

@Data
public class AccountInfoResponse {

    private int responseCode;
    private boolean success;
    private String message;
    private Account account;

    public AccountInfoResponse() {
    }

    public AccountInfoResponse(int responseCode, boolean success, String message, Account account) {
        this.responseCode = responseCode;
        this.success = success;
        this.message = message;
        this.account = account;
    }
}
