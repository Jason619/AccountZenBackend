package com.group.AccountZen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.AccountZen.entity.Account;
import com.group.AccountZen.exception.InsufficientBalanceException;
import com.group.AccountZen.repository.AccountRepository;

import jakarta.persistence.EntityNotFoundException;

/**
 * The AccountService class provides business logic for account-related operations.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Creates a new account with the specified account type.
     *
     * @param accountType The type of the account to create.
     * @return The newly created account.
     */
    public Account createAccount(String accountType) {
        Account account = new Account();
        account.setAccountType(accountType);
        account.setActive(true);
        account.setBalance(0);
        return accountRepository.save(account);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id The ID of the account to retrieve.
     * @return The account if found, otherwise null.
     */
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    /**
     * Deposits cash into the specified account.
     *
     * @param accountId The ID of the account to deposit cash into.
     * @param amount     The amount of cash to deposit.
     * @return The updated account after the deposit.
     */
    public Account depositCash(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            double currentBalance = account.getBalance();
            account.setBalance(currentBalance + amount);
            return accountRepository.save(account);
        }
        return null;
    }

    /**
     * Withdraws cash from the specified account.
     *
     * @param accountId The ID of the account to withdraw cash from.
     * @param amount     The amount of cash to withdraw.
     * @return The updated account after the withdrawal.
     * @throws EntityNotFoundException        If the specified account does not exist.
     * @throws InsufficientBalanceException If the account has insufficient balance for the withdrawal.
     */
    public Account withdrawCash(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            throw new EntityNotFoundException("Account not found with id: " + accountId);
        }

        double currentBalance = account.getBalance();
        if (currentBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal from account id: " + accountId);
        }

        double updatedBalance = currentBalance - amount;
        account.setBalance(updatedBalance);
        return accountRepository.save(account);
    }

    /**
     * Closes the specified account.
     *
     * @param accountId The ID of the account to close.
     * @return true if the account was successfully closed, otherwise false.
     */
    public boolean closeAccount(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            account.setActive(false);
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}