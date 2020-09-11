package com.revature.services;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.AppUser;
import com.revature.models.Role;
import com.revature.repos.AppUserRepo;

import java.util.Optional;
import java.util.Set;


/**
 * Service Classes for the users to check for errors in the inputs and communicate with the Repo class.
 */
public class UserService {

    private AppUserRepo userRepo = new AppUserRepo();

//    public UserService(AppUserRepo repo) {
//        userRepo = repo;
//    }


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


    public void updateUser(AppUser employee) {

        Optional<AppUser> user = userRepo.findUserByUsername(employee.getUsername());

        if (!isUserValid(user.get())) {
//            app.invalidateCurrentUser();
            throw new InvalidInputException("Invalid credentials given for registration.");
        }
//        if (user.get().getRole() != Role.ADMIN) {
//            app.invalidateCurrentUser();
//            throw new AuthenticatorException("You aren't allowed to update any users!");
//            //to  do return home
//        }

        userRepo.updateAppUser(employee);

    }

    public void deleteUser(int id) {
        //if the user isn't valid, invalidate them and throw an exception.

        Optional<AppUser> user = userRepo.findUserById(id);

        if (!isUserValid(user.get())) {
            throw new InvalidInputException("Invalid credentials given for deletion.");
        }

        userRepo.deleteEmployee(id);

    }

    public Set<AppUser> getAllUsers() {

        Set<AppUser> users = userRepo.findAllUsers();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return users;
    }


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

    public boolean isUsernameAvailable(String username) {
        AppUser user = userRepo.findUserByUsername(username).orElse(null);
        return user == null;
    }

    public boolean isEmailAvailable(String email) {
        AppUser user = userRepo.findUserByEmail(email).orElse(null);
        return user == null;
    }

}
