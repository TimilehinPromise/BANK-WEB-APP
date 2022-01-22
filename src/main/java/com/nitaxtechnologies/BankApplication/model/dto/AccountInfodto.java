package com.nitaxtechnologies.BankApplication.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountInfodto {
    @NotEmpty
    String password;

    public AccountInfodto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountInfodto() {
    }
}
