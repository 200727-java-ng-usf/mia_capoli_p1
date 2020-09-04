package com.revature.controllers;

import com.revature.repos.AppUserRepo;
import com.revature.services.UserService;


import javax.servlet.http.HttpServletRequest;

import static com.revature.services.UserService.app;

/**
 * Controllers handle the business logic of an endpoint
 */
public class LoginController {

    private static AppUserRepo AppUserRepo = new AppUserRepo();
    private static UserService userService = new UserService(AppUserRepo);


    public static String login(HttpServletRequest req) {



		/*
		You may want to implement route guarding here for your endpoints
		You could use filters (middleware) to do this.

		For example, you may want to make sure only ADMINS can access admin endpoints.
		 */
        //ensure that the method is a POST http method, else send them back to the login page.


        try {
            if (!req.getMethod().equals("POST")) {
                return  "/html/login.html";
            }

            System.out.println();
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            userService.authenticate(username, password);


            req.getSession().setAttribute("loggedUsername", username);
            req.getSession().setAttribute("loggedPassword", password);
            return "/html/home.html";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  "/html/login.html";
    }
}
