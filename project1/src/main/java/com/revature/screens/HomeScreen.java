package com.revature.screens;

import java.io.IOException;

import static com.revature.AppDriver.app;

/**
 * Render the Home Screen and navigate to screens from the home page.
 */
public class HomeScreen extends Screen {

    public HomeScreen() {
        super("HomeScreen", "/home");
    }

    /**
     * Render the Home Screen
     */
    @Override
    public void render() {
        try {
            System.out.println("Welcome to the Bank of Capoli!\n");
            System.out.println("1) Login");
            System.out.println("2) Register");
            System.out.println("3) Exit Application");
            System.out.print("> ");
            String userSelection = app.getConsole().readLine().trim();

            //in each case, navigate to the proper page, or set the app to not run.
            switch (userSelection) {
                case "1":
                    app.getRouter().navigate("/login");
                    break;
                case "2":
                    app.getRouter().navigate("/register");
                    break;
                case "3":
                    app.setAppRunning(false);
                    break;
                default:
                    System.err.println("Invalid selection!");
                    app.getRouter().navigate("/home");
            }

        } catch (IOException e) {
            System.err.println("Please enter a valid selection!");
            app.getRouter().navigate("/home");
        } catch (Exception e) {
            System.err.println("A problem occurred.");
            app.getRouter().navigate("/home");
        }
    }
}