package com.nitaxtechnologies.BankApplication.model;

import lombok.Data;

@Data
public class Response {

   private int responseCode;
    private boolean success;
    private String message;
}
