package com.revature.services;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.AppUserRepo;
import com.revature.util.AppUserComparator;

import java.util.*;


/**
 * Service Classes for the users to check for errors in the inputs and communicate with the Repo class.
 */
public class UserService {

    public AppUserRepo userRepo = new AppUserRepo();


    /**
     * Authenticate the provided username and password.
     * @param username
     * @param password
     * @return
     */
    public AppUser authenticate(String username, String password) {

        Optional<AppUser> _authUser = (userRepo.findUser(username, password));
        System.out.println(_authUser);

        if (!_authUser.isPresent()) {

            throw new AuthenticatorException();
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
            throw new InvalidInputException("Invalid credentials given for registration.");
        }

        Optional<AppUser> _existingUser = userRepo.findUserByUsername(newUser.getUsername());

        if (_existingUser.isPresent()) {
            throw new AuthenticatorException("Provided username is already in use!");
        }


        Optional<AppUser> existingUser_ = userRepo.findUserByEmail(newUser.getEmail());

        if (existingUser_.isPresent()) {
            System.out.println("in is present");
            throw new InvalidRequestException("Provided email is already in use!");
        }


        newUser.setRole(Role.EMPLOYEE);
        userRepo.save(newUser);

    }

    /**
     * Update an existing user and check if it's valid.
     * @param employee
     * @return
     */
    public AppUser updateUser(AppUser employee) {

        Optional<AppUser> user = userRepo.findUserByUsername(employee.getUsername());

        if (!user.isPresent()) {
            throw new AuthenticatorException("This user does not exist, therefore cannot be updated.");
        }

        if (!isUserValid(employee)) {
            throw new InvalidInputException("Invalid credentials given for registration.");
        }

        userRepo.updateAppUser(employee);

        return userRepo.findUserByUsername(employee.getUsername()).get();

    }

    /**
     * Delete an existing user.
     * @param id
     * @return
     */
    public boolean deleteUser(int id) {

        Optional<AppUser> user = userRepo.findUserById(id);

        if (!user.isPresent()) {
            throw new InvalidInputException("Invalid credentials given for deletion.");
        }
        userRepo.deleteEmployee(id);
        return true;
    }

    /**
     * Get all existing users.
     * @return
     */
    public ArrayList<AppUser> getAllUsers() {

        ArrayList<AppUser> users = userRepo.findAllUsers();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        ArrayList<AppUser> list = new ArrayList<>(users);
        list.sort(new AppUserComparator());

        return list;
    }

    /**
     * Get a user by their id.
     * @param id
     * @return
     */
    public AppUser getUserById(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("The provided Id cannot be less than or equal to 0!");
        }

        return userRepo.findUserById(id)
                .orElseThrow(ResourceNotFoundException::new);
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

    /**
     * Check if the provided username is available.
     * @param username
     * @return
     */
    public boolean isUsernameAvailable(String username) {
        AppUser user = userRepo.findUserByUsername(username).orElse(null);
        return user == null;
    }

    /**
     * Check if the provided password is available.
     * @param email
     * @return
     */
    public boolean isEmailAvailable(String email) {
        AppUser user = userRepo.findUserByEmail(email).orElse(null);
        return user == null;
    }

}
