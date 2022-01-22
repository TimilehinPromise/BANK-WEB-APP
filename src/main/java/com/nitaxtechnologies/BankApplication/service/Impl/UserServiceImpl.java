package com.nitaxtechnologies.BankApplication.service.Impl;

import com.nitaxtechnologies.BankApplication.entities.*;
import com.nitaxtechnologies.BankApplication.model.Account;
import com.nitaxtechnologies.BankApplication.model.Response;
import com.nitaxtechnologies.BankApplication.model.TransactionType;
//import com.nitaxtechnologies.BankApplication.model.dto.*;
import com.nitaxtechnologies.BankApplication.model.dto.DepositDTO;
import com.nitaxtechnologies.BankApplication.model.dto.AccountWithdrawalDTO;
import com.nitaxtechnologies.BankApplication.model.dto.CreateAccountDTO;
import com.nitaxtechnologies.BankApplication.model.AccountInfoResponse;
import com.nitaxtechnologies.BankApplication.model.dto.TransactionResponse;
import com.nitaxtechnologies.BankApplication.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.nitaxtechnologies.BankApplication.model.TransactionType.DEPOSIT;
import static com.nitaxtechnologies.BankApplication.model.TransactionType.WITHDRAWAL;

@Service
public class UserServiceImpl implements UserService {
    private static final String NO_ACCOUNT_FOUND =
            "Unable to find an account matching this sort code and account number";
    private static final Double MINIMUM_AMOUNT= 500.00;
    private static final String ACCOUNT_NOT_FOUND ="Account Does Not Exist";
    private static final String ACCOUNT_EXIST ="Account Name already exists";
    private static final String WITHDRAWAL_AMOUNT_LESS_THAN_REQUIRED= "Withdrawal Not Processed Because Account Balance Will Become Lower Than Required Amount";
    private static final TransactionType WITHDRAWALS = WITHDRAWAL;
    private static final TransactionType DEPOSITS = DEPOSIT;
    private static final String SUCCESS_WITHDRAW ="Withdraw Successful";
    private static final String SUCCESS_DEPOSIT = "Deposit Successful";

    private List<UserAccount> userAccountList = new ArrayList<>(Arrays.asList(
            new UserAccount("Timmy-test1","blameJohnDoe",6000.00,"45657677378")
    ));
    private List<Transactions> transactionsList = new ArrayList<>();

    ArrayList<UserLogin> userDetails = new ArrayList<>();
    @Override
    public ResponseEntity<?> createAccount(CreateAccountDTO dto){
        String generatedNo = null;
        Response response = new Response();
        boolean found = userAccountList.stream()
                .anyMatch(userAccount -> userAccount.getAccountName().equals(dto.getAccountName()));
        if (found == true){
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage(ACCOUNT_EXIST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        generatedNo =generateAccountNo();
        String finalGeneratedNo = generatedNo;
        boolean existingAccountNumber = userAccountList.stream()
                            .anyMatch(userAccount -> userAccount.getAccountNumber().equals(finalGeneratedNo));
            if (existingAccountNumber== true){
                generatedNo = generateAccountNo();
            }
            userDetails.add(new UserLogin(generatedNo,dto.getAccountPassword()));
        userAccountList.add( new UserAccount(dto.getAccountName(),dto.getAccountPassword(),dto.getInitialDeposit(),generatedNo));
        response.setResponseCode(200);
        response.setSuccess(true);
        response.setMessage("Account Successfully Created With Account Number: "+generatedNo+"");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Override
    public ResponseEntity<?> makeWithdrawal(AccountWithdrawalDTO withdrawalDTO){
        Response response = new Response();
        boolean found = userAccountList.stream()
                .anyMatch(userAccount -> userAccount.getAccountNumber().equals(withdrawalDTO.getAccountNumber()) && userAccount.getAccountPassword().equals(withdrawalDTO.getAccountPassword()));

        if (found == false){
                response.setResponseCode(400);
                response.setSuccess(false);
                response.setMessage(ACCOUNT_NOT_FOUND);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            List<UserAccount> requiredAccount = userAccountList.stream()
                    .filter(userAccount -> userAccount.getAccountNumber().equals(withdrawalDTO.getAccountNumber()) && userAccount.getAccountPassword().equals(withdrawalDTO.getAccountPassword()))
                    .collect(Collectors.toList());
            String accountNo= requiredAccount.get(0).getAccountNumber();
            Double currentBalance = requiredAccount.get(0).getAccountBalance();
            Double newAmount = currentBalance - withdrawalDTO.getWithdrawnAmount();
            if ((newAmount) < MINIMUM_AMOUNT){
                response.setResponseCode(400);
                response.setSuccess(false);
                response.setMessage(WITHDRAWAL_AMOUNT_LESS_THAN_REQUIRED);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
         userAccountList.stream()
                .filter(userAccount -> userAccount.getAccountNumber().equals(withdrawalDTO.getAccountNumber()) && userAccount.getAccountPassword().equals(withdrawalDTO.getAccountPassword()))
                 .forEach(userAccount -> userAccount.setAccountBalance(newAmount));

            String narration = "Withdrew ₦"+withdrawalDTO.getWithdrawnAmount()+" from ₦"+currentBalance+".";
         transactionsList.add(new Transactions(LocalDateTime.now(),WITHDRAWALS,narration, withdrawalDTO.getWithdrawnAmount(),newAmount,accountNo));
         response.setResponseCode(200);
         response.setSuccess(true);
         response.setMessage(SUCCESS_WITHDRAW);
         return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @Override
    public ResponseEntity<?> makeDeposit(DepositDTO depositDTO){
        Response response = new Response();
        boolean found = userAccountList.stream()
                .anyMatch(userAccount -> userAccount.getAccountNumber().equals(depositDTO.getAccountNumber()));
        if (found == false){
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage(ACCOUNT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        List<UserAccount> requiredAccount = userAccountList.stream()
                .filter(userAccount -> userAccount.getAccountNumber().equals(depositDTO.getAccountNumber()))
                .collect(Collectors.toList());
        Double currentBalance = requiredAccount.get(0).getAccountBalance();
        String accountNo= requiredAccount.get(0).getAccountNumber();
        Double newAmount = currentBalance + depositDTO.getAmount();
        userAccountList.stream()
                .filter(userAccount -> userAccount.getAccountNumber().equals(depositDTO.getAccountNumber()))
                .forEach(userAccount -> userAccount.setAccountBalance(newAmount));
        String narration = "Deposited ₦"+depositDTO.getAmount()+"";
        transactionsList.add(new Transactions(LocalDateTime.now(),DEPOSITS,narration, depositDTO.getAmount(), newAmount,accountNo));
        response.setResponseCode(200);
        response.setSuccess(true);
        response.setMessage(SUCCESS_DEPOSIT);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    public ResponseEntity<?> getAccountStatements(String accountNumber){
      List<Transactions> transactionList=  transactionsList.stream()
              .filter(transactions -> transactions.getAccountNumber().equals(accountNumber))
              .collect(Collectors.toList());
       ArrayList<TransactionResponse> transactionResponse = new ArrayList<TransactionResponse>();
        for (Transactions transactions:transactionList) {
            transactionResponse.add(new TransactionResponse(transactions.getTransactionDate(),transactions.getTransactionType(),transactions.getNarration(),transactions.getAmount(),transactions.getAccountBalance()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }
    @Override
    public ResponseEntity<?> getAccountInfo(String accountNumber, String accountPassword){
        Response response = new Response();
        boolean found = userAccountList.stream()
                .anyMatch(userAccount -> userAccount.getAccountNumber().equals(accountNumber) && userAccount.getAccountPassword().equals(accountPassword));
        if (found == false){
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage(ACCOUNT_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        List<UserAccount> requiredAccount = userAccountList.stream()
                .filter(userAccount -> userAccount.getAccountNumber().equals(accountNumber))
                .collect(Collectors.toList());
        String name = requiredAccount.get(0).getAccountName();
        String accNumber = requiredAccount.get(0).getAccountNumber();
        Double balance = requiredAccount.get(0).getAccountBalance();
        AccountInfoResponse accountInfoResponse = new AccountInfoResponse();
        accountInfoResponse.setResponseCode(200);
        accountInfoResponse.setSuccess(true);
        accountInfoResponse.setMessage("Below are your account details");
        accountInfoResponse.setAccount(new Account(name,accNumber,balance));
        return ResponseEntity.status(HttpStatus.OK).body(accountInfoResponse);
    }
    public UserLogin findDetails(String accountNo){
        Response response = new Response();
        List<UserAccount> requiredAccount = userAccountList.stream()
                .filter(userAccount -> userAccount.getAccountNumber().equals(accountNo))
                .collect(Collectors.toList());
        if (requiredAccount.isEmpty()){
            response.setResponseCode(400);
            response.setSuccess(false);
            response.setMessage(ACCOUNT_NOT_FOUND);
        }
        UserLogin userDetailss = new UserLogin();
        userDetailss.setAccountNo(requiredAccount.get(0).getAccountNumber());
        userDetailss.setAccountPassword(requiredAccount.get(0).getAccountPassword());
        return userDetailss;

    }

    private String generateAccountNo(){
        int m = (int) Math.pow(10, 9);
        String generateAccountNo = String.valueOf(m + new Random().nextInt(9 * m));
        return generateAccountNo;
    }
}

