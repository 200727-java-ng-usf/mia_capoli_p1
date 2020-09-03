package com.revature.servlets;

import com.revature.models.AppUser;
import com.revature.models.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardingMasterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher(RequestHelper.process(req)).forward(req,resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String firstName = req.getParameter("first name");
		String lastName = req.getParameter("last name");
		String email = req.getParameter("email");

		AppUser tempUser = new AppUser(username, password, firstName, lastName, email);

		req.getRequestDispatcher(RequestHelper.process(req)).forward(req,resp);
	}
}