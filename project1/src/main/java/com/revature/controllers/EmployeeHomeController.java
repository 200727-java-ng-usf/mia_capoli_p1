package com.revature.controllers;

import javax.servlet.http.HttpServletRequest;

public class EmployeeHomeController {
	public static String home(HttpServletRequest req){
		return "/html/home.html";
	}
}