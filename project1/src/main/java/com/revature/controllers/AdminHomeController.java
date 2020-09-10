package com.revature.controllers;

import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.models.AppUser;
import com.revature.repos.AppUserRepo;
import com.revature.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminHomeController {

	private static UserService userService = new UserService();

	public static String home(HttpServletRequest req){
		return "/html/home.html";
	}

	public static String updateUser(HttpServletRequest req) throws IOException {

		if (!req.getMethod().equals("POST")) {
			return "/html/register.html";
		}

		try {
			//Todo implement when fields are null get the current value and return that
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String firstName = req.getParameter("firstName");
			String lastName = req.getParameter("lastName");
			String email = req.getParameter("email");

			AppUser employee;
			employee = new AppUser(username, password, firstName, lastName, email);

			userService.updateUser(employee);

			return "/api/home";

		} catch (AuthenticatorException | InvalidInputException e) {
			e.printStackTrace();
			return "/api/invalidinput";
		}
	}
}
