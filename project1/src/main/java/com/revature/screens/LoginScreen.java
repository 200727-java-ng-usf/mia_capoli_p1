package com.revature.screens;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.services.UserService;

import java.io.IOException;

import static com.revature.AppDriver.app;


/**
 * Render the Login Screen and log into your account.
 */
public class LoginScreen extends Screen {

    private UserService userService;

    public LoginScreen(UserService userService) {

        super("LoginScreen", "/login");
        this.userService = userService;
    }

    /**
     * Render the Login Screen.
     */
    @Override
    public void render() {
        String username, password;

        try {
            System.out.println("Please provide your login credentials");
            System.out.print("Username: ");
            username = app.getConsole().readLine();
            System.out.print("Password: ");
            password = app.getConsole().readLine();

            //Authenticate the user inputted.
            userService.authenticate(username, password);
            //Navigate to the proper screen.
            if (app.isSessionValid()) {
                app.getRouter().navigate("/selectAccount");
            }

        } catch (AuthenticatorException ae) {
            System.err.println("Provided user does not exist!");
            app.getRouter().navigate("/login");
        } catch (InvalidInputException | IOException ie) {
            System.err.println("Invalid user credentials given!");
            app.getRouter().navigate("/login");
        } catch (Exception e) {
            System.err.println("A problem occurred.");
            app.getRouter().navigate("/login");
        }
    }

}
