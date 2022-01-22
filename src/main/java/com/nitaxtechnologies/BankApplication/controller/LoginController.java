package com.nitaxtechnologies.BankApplication.controller;

import com.nitaxtechnologies.BankApplication.Utility.JWTUtility;
import com.nitaxtechnologies.BankApplication.model.JwtRequest;
import com.nitaxtechnologies.BankApplication.model.JwtResponse;
import com.nitaxtechnologies.BankApplication.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getAccountNo(),
                            jwtRequest.getAccountPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("INVALID_CREDENTIALS", ex);
        }
        final UserDetails userDetails
                = loginService.loadUserByUsername(jwtRequest.getAccountNo());
        final String token =
                jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
    }
