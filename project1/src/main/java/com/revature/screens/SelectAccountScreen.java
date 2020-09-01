package com.revature.screens;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.Account;
import com.revature.services.AccountService;

import java.io.IOException;
import java.util.Set;

import static com.revature.AppDriver.app;

/**
 * Render the Select Account Screen and select an account.
 */
public class SelectAccountScreen extends Screen {

    private AccountService accountService;

    public SelectAccountScreen(AccountService accountService) {
        super("SelectAccountScreen", "/selectAccount");
        this.accountService = accountService;
    }

    /**
     * Render the Select Account Screen.
     */
    @Override
    public void render() {
        try {
            Set<Account> accountSet = accountService.returnUsersAccounts(app.getCurrentUser().getId());

            for (Account userAccount : accountSet) {
                System.out.println(userAccount.toString());
            }

            System.out.println("Please enter the account number that you would like to access:");
            System.out.print("> ");
            int userSelection = Integer.parseInt(app.getConsole().readLine().trim());
            //Select the account in the repo by user input
            Account currentAccount = accountService.findAccountByAccountId(userSelection);
            //set the current account and navigate to the dashboard
            app.setCurrentAccount(currentAccount);
            app.getRouter().navigate("/Dashboard");

        } catch (IOException | InvalidInputException e) {
            System.err.println("Please enter a valid account number!");
            app.getRouter().navigate("/selectAccount");
        } catch (AuthenticatorException ae) {
            System.err.println("Please enter an account number that you have an account for!");
            app.getRouter().navigate("/selectAccount");
        } catch (Exception e) {
            System.err.println("A problem occurred.");
            app.getRouter().navigate("/selectAccount");
        }
    }
}
