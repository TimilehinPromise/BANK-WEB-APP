package com.nitaxtechnologies.BankApplication.service;

import com.nitaxtechnologies.BankApplication.model.dto.AccountWithdrawalDTO;
import com.nitaxtechnologies.BankApplication.model.dto.CreateAccountDTO;
import com.nitaxtechnologies.BankApplication.model.dto.DepositDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {

     ResponseEntity<?>  createAccount(CreateAccountDTO dto);
     ResponseEntity<?> makeWithdrawal(AccountWithdrawalDTO withdrawalDTO);
     ResponseEntity<?> makeDeposit(DepositDTO depositDTO);
     ResponseEntity<?> getAccountStatements(String accountNumber);
     ResponseEntity<?> getAccountInfo(String accountNumber, String accountPassword);

}
