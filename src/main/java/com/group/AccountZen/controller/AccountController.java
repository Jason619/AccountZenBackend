package com.group.AccountZen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.AccountZen.dto.AccountOperationRequest;
import com.group.AccountZen.dto.AccountTransactionRequest;
import com.group.AccountZen.dto.CreateAccountRequest;
import com.group.AccountZen.entity.Account;
import com.group.AccountZen.service.AccountService;

/**
 * The AccountController class handles RESTful API endpoints related to account management.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * Creates a new account.
     *
     * @param request The request object containing the account type.
     * @return ResponseEntity containing the created account details or an error message.
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request) {
        Account account = accountService.createAccount(request.getAccountType());
        if (account != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create account");
        }
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param request The request object containing the account ID.
     * @return ResponseEntity containing the account details if found or an error message.
     */
    @PostMapping("/get")
    public ResponseEntity<?> getAccountById(@RequestBody AccountOperationRequest request) {
        Account account = accountService.getAccountById(request.getAccountId());
        if (account != null) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.badRequest().body("Account ID Invalid. Please enter a valid account ID.");
        }
    }

    /**
     * Deposits cash into an account.
     *
     * @param request The request object containing the account ID and the amount to deposit.
     * @return ResponseEntity containing the updated account details or an error message.
     */
    @PostMapping("/deposit")
    public ResponseEntity<?> depositCash(@RequestBody AccountTransactionRequest request) {
        Account account = accountService.depositCash(request.getAccountId(), request.getAmount());
        if (account != null) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.badRequest().body("Account ID Invalid. Please enter a valid account ID.");
        }
    }

    /**
     * Withdraws cash from an account.
     *
     * @param request The request object containing the account ID and the amount to withdraw.
     * @return ResponseEntity containing the updated account details or an error message.
     */
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawCash(@RequestBody AccountTransactionRequest request) {
        Account account = accountService.withdrawCash(request.getAccountId(), request.getAmount());
        if (account != null) {
            return ResponseEntity.ok().body(account);
        } else {
            return ResponseEntity.badRequest().body("Account ID Invalid. Please enter a valid account ID.");
        }
    }

    /**
     * Closes an account.
     *
     * @param request The request object containing the account ID.
     * @return ResponseEntity containing a success message or an error message.
     */
    @PostMapping("/close")
    public ResponseEntity<?> closeAccount(@RequestBody AccountOperationRequest request) {
        boolean closed = accountService.closeAccount(request.getAccountId());
        if (closed) {
            return ResponseEntity.ok().body("Account closed successfully");
        } else {
            return ResponseEntity.badRequest().body("Account ID Invalid. Please enter a valid account ID.");
        }
    }

}