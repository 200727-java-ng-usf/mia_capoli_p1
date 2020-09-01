package com.revature.services;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.AppUser;
import com.revature.repos.AppUserRepo;

import java.util.Optional;

import static com.revature.AppDriver.app;

/**
 * Service Classes for the users to check for errors in the inputs and communicate with the Repo class.
 */
public class UserService {

    private AppUserRepo userRepo;

    public UserService(AppUserRepo repo) {
        userRepo = repo;
    }


    /**
     * Authenticate the provided username and password.
     * @param username
     * @param password
     * @return
     */
    public AppUser authenticate(String username, String password) {

        Optional<AppUser> _authUser = (userRepo.findUser(username, password));
        //if the user isn't present in the persistence layer, throw an exception. Otherwise set the current user to the provided user credentials' user.
        if (!_authUser.isPresent()) {
            app.invalidateCurrentUser();
            throw new AuthenticatorException();
        } else {
            app.setCurrentUser(_authUser.get());

        }

        return _authUser.get();
    }

    /**
     * Register a new user.
     * @param newUser
     */
    public void registration(AppUser newUser) {
        //if the user isn't valid, invalidate them and throw an exception.
        if (!isUserValid(newUser)) {
            app.invalidateCurrentUser();
            throw new InvalidInputException("Invalid credentials given for registration.");
        }

        //attempt to get the user provided in the repo.
        Optional<AppUser> _existingUser = userRepo.findUserByUsername(newUser.getUsername());

        //if the user already exists, invalidate the current user and throw an exception.
        if (_existingUser.isPresent()) {
            app.invalidateCurrentUser();
            throw new AuthenticatorException("Provided username is already in use!");
        }

        //save the current user in the persistence layer and set the current user
        userRepo.save(newUser);
        app.setCurrentUser(newUser);
    }

    /**
     * Check if the provided user is valid.
     * @param user
     * @return
     */
    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        if (user.getPassword() == null || user.getPassword().trim().equals("")) return false;
        return true;
    }

}
