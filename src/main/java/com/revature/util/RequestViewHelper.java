package com.revature.util;

import com.revature.controllers.AdminHomeController;
import com.revature.controllers.RegisterController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestViewHelper {

    public String process(HttpServletRequest req) throws IOException {
        switch (req.getRequestURI()) {

            case "/login.view":
            case "/ExpenseReimbursementSystem/login.view":
                return "partials/login.html";
            case "/home.view":
            case "/ExpenseReimbursementSystem/home.view":
                String principal = (String) req.getSession().getAttribute("principal");
                if (principal == null || principal.equals("")) {
                    return "partials/login.html";
                }
                return "partials/home.html";
            case "/register.view":
            case "/ExpenseReimbursementSystem/register.view":
                return "partials/register.html";
            default:
                return null;

        }



    }
}
