package com.revature.services;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.NegativeException;
import com.revature.exceptions.OverdraftException;
import com.revature.models.Account;
import com.revature.repos.AccountRepo;

import java.util.Optional;
import java.util.Set;

import static com.revature.AppDriver.app;

/**
 * Service Classes for the accounts to check for errors in the inputs and communicate with the Repo class.
 */
public class AccountService {

    private AccountRepo accountRepo;

    public AccountService(AccountRepo repo) {
        accountRepo = repo;
    }

    /**
     * Find the account in the repo by the account Id provided.
     * @param accountId
     * @return
     */
    public Account findAccountByAccountId(int accountId) {
        //if the provided id is less than or equal to zero, the account is invalid.
        if (accountId <= 0) {
            throw new InvalidInputException("Invalid account credentials given!");
        }

        //Find the account in the persistence layer
        Optional<Account> _authAccount = accountRepo.findAccountByAccountId(accountId);
        //if the account does not exist
        if (!_authAccount.isPresent()) {
            throw new AuthenticatorException("No such account exists!");
        }

        //set the current account to the found account.
        app.setCurrentAccount(_authAccount.get());
        return _authAccount.get();

    }

    /**
     * Register the user provided.
     * @param newAccount
     */
    public void registration(Account newAccount) {

        //if the account isn't valid (null or zero values) throw an exception
        if (!isAccountValid(newAccount)) {
            throw new InvalidInputException("Invalid credentials given for registration.");
        }

        //get an optional of the provided account id by the provided account id
        Optional<Account> existingAccount = accountRepo.findAccountByAccountId(newAccount.getAccountId());

        //if the account id exists then throw an exeption because there cannot be more than one account with the same id.
        if (existingAccount.isPresent()) {
            throw new AuthenticatorException("Provided account id is already in use!");
        }

        //save the account in the persistence layer.
        accountRepo.save(newAccount);
        app.setCurrentAccount(newAccount);
    }


    /**
     *  Return all of the Current user's accounts
     * @param currentUserId
     * @return
     */
    public Set<Account> returnUsersAccounts(int currentUserId) {

        //If the id provided is less than or equal to zero, throw an exception.
        if (currentUserId <= 0) {
            throw new InvalidInputException("Please enter a positive, non-zero number!");
        }

        //get a set of user accounts from the persistence layer.
        Set<Account> usersAccounts = accountRepo.findUsersAccounts(currentUserId);

        //if the accounts set is empty, throw an exception.
        if (usersAccounts.isEmpty()) {
            throw new AuthenticatorException("No accounts exist for this user.");
        }
        return usersAccounts;
    }

    /**
     * Check if an entered account is valid.
     * @param account
     * @return
     */
    public boolean isAccountValid(Account account) {
        if (account == null) return false;
        if (account.getAccountName() == null || account.getAccountName().trim().equals("")) return false;
        if (account.getAccountId() == 0 || account.getAccountId() < 1) return false;
        return true;
    }

    /**
     * Calculate funds for an account.
     * @param isAdd
     * @param fundsProvided
     * @return
     */
    public double fundsUpdate(boolean isAdd, double fundsProvided) {
        //get the current account and the provided funds to add or withdraw
        Account currentAccount = app.getCurrentAccount();
        double balance = currentAccount.getBalance();
        double temp = fundsProvided;

        //if the number is negative or zero, throw an exception
        if (temp <= 0) {
            throw new NegativeException("Please enter a positive, non-zero number!");
        } else {
            //if the funds are being added, add them, if not, withdraw
            if (isAdd) {
                balance = temp + balance;
            } else if (balance < temp) {
                //if the funds are being withdrawn and they go over the account's balance, throw an overdraft exception.
                throw new OverdraftException("This account does not support overdrafting.");
            } else {
                balance = balance - temp;
            }

        }
        currentAccount.setBalance(balance);
        accountRepo.updateBalance(balance);
        return balance;
    }
}
