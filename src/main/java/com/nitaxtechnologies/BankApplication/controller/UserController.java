package com.nitaxtechnologies.BankApplication.controller;

import com.nitaxtechnologies.BankApplication.model.dto.AccountInfodto;
import com.nitaxtechnologies.BankApplication.model.dto.AccountWithdrawalDTO;
import com.nitaxtechnologies.BankApplication.model.dto.CreateAccountDTO;
import com.nitaxtechnologies.BankApplication.model.dto.DepositDTO;
import com.nitaxtechnologies.BankApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST,value = "/create_account",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO){
        return this.userService.createAccount(createAccountDTO);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/withdrawal",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeWithdrawal(@Valid @RequestBody AccountWithdrawalDTO withdrawalDTO ){
        return this.userService.makeWithdrawal(withdrawalDTO);
    }
    @RequestMapping(method = RequestMethod.POST,value = "/deposit",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> makeDeposit(@Valid @RequestBody DepositDTO depositDTO ){
        return this.userService.makeDeposit(depositDTO);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/account_statement/{accountNumber}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccountStatements(@PathVariable("accountNumber")String accountNumber){
        return this.userService.getAccountStatements(accountNumber);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/account_info/{accountNumber}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccountInfo(@PathVariable("accountNumber")String accountNumber,
                                            @Valid @RequestBody AccountInfodto accountInfodto){
        return this.userService.getAccountInfo(accountNumber, accountInfodto.getPassword());
    }




}
