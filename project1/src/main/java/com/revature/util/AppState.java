package com.revature.util;

import com.revature.models.AppUser;
import com.revature.repos.AppUserRepo;
import com.revature.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * Appstate - instantiate singletons and add screens to the router.
 */
public class AppState {
    private BufferedReader console;
    private AppUser currentUser;
    private boolean appRunning;
    private boolean add;


    public AppState() {

        appRunning = true;
        console = new BufferedReader(new InputStreamReader(System.in));

        final AppUserRepo userRepository = new AppUserRepo();
        final UserService userService = new UserService(userRepository);
    }

    //get the buffered reader
    public BufferedReader getConsole() {
        return console;
    }

    //get + set the current user
    public AppUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(AppUser currentUser) {
        this.currentUser = currentUser;
    }

    //get and set if the app is running
    public boolean isAppRunning() {
        return appRunning;
    }

    public void setAppRunning(boolean appRunning) {
        this.appRunning = appRunning;
    }

    //get and set the current account

    //invalidate the current user
    public void invalidateCurrentUser() {
        currentUser = null;
    }

    //check if the session is valid
    public boolean isSessionValid() {
        return (this.currentUser != null);
    }

    //check if the protocol is to add or withdraw funds
    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean falseAdd) {
        this.add = add;
    }
}
