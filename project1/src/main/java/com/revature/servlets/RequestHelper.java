package com.revature.servlets;

import com.revature.controllers.AdminHomeController;
import com.revature.controllers.LoginController;
import com.revature.controllers.RegisterController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestHelper {

	public static String process(HttpServletRequest req) throws IOException {

		String ret = ("THIS is the current URI active: " + req.getRequestURI());
		System.out.println(ret);
		switch(req.getRequestURI()){
			case "/ExpenseReimbursementSystem/api/login":
				System.out.println("in login case");
				return LoginController.login(req);
			case "/ExpenseReimbursementSystem/api/home":
				System.out.println("in home case");
				return AdminHomeController.home(req);
			case "/ExpenseReimbursementSystem/api/register":
				System.out.println("in register case");
				return RegisterController.registerNewUser(req);
			default:
				System.out.println("in default");
				return "/html/badlogin.html";
		}
	}
}
