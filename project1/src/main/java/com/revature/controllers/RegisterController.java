package com.revature.controllers;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.AppUser;
import com.revature.repos.AppUserRepo;
import com.revature.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RegisterController {

    private static AppUserRepo userRepo = new AppUserRepo();
    private static UserService userService = new UserService(userRepo);

    public static String registerNewUser(HttpServletRequest req) throws IOException {

        if (!req.getMethod().equals("POST")) {
            return "/html/register.html";
        }

        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            // TODO b sure the user is an admin
            AppUser employee;
            if (!(username.equals(null) || password.equals(""))) {
                // this logic will trigger when the amount is null or the type is empty
                return "/api/invalidinput";
            } else {
                employee = new AppUser(username, password, firstName, lastName, email);
                System.out.println(employee);
            }


            userService.registration(employee);
            System.out.println("register");

            req.getSession().setAttribute("loggedUsername", username);
            req.getSession().setAttribute("loggedPassword", password);
            req.getSession().setAttribute("loggedFirstName", firstName);
            req.getSession().setAttribute("loggedLastName", lastName);
            req.getSession().setAttribute("loggedEmail", email);
            req.getSession().setAttribute("loggedRole", employee.getRole());

            return "/api/home";

        } catch (AuthenticatorException | InvalidInputException e) {
            e.printStackTrace();
            return "/api/badlogin.html";
        }
    }

}
