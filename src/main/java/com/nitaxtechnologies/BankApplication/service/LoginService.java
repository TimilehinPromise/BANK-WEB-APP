package com.nitaxtechnologies.BankApplication.service;

import com.nitaxtechnologies.BankApplication.entities.UserAccount;
import com.nitaxtechnologies.BankApplication.entities.UserLogin;
import com.nitaxtechnologies.BankApplication.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
UserServiceImpl userService;
UserLogin userLogin;
    @Override
    public UserDetails loadUserByUsername(String accountNo) throws UsernameNotFoundException {
        ArrayList<UserAccount> userAccountList = new ArrayList<>();
         userLogin = userService.findDetails(accountNo);

         return new User(userLogin.getAccountNo(),userLogin.getAccountPassword(),new ArrayList<>());
    }
}
