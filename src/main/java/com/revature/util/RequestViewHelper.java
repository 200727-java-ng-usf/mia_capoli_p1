package com.revature.util;

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
                System.out.println("IN HOME");
                return "partials/home.html";
            case "/adminhome.view":
            case "/ExpenseReimbursementSystem/adminhome.view":
                String principal_home = (String) req.getSession().getAttribute("principal");
                if (principal_home == null || principal_home.equals("")) {
                    return "partials/login.html";
                }
                return "partials/homeAdmin.html";
            case "/delete.view":
            case "/ExpenseReimbursementSystem/delete.view":
                String principal_update = (String) req.getSession().getAttribute("principal");
                if (principal_update == null || principal_update.equals("")) {
                    return "partials/login.html";
                }
                return "partials/delete.html";
            case "/register.view":
            case "/ExpenseReimbursementSystem/register.view":
                return "partials/register.html";
            case "/financehome.view":
            case "/ExpenseReimbursementSystem/financehome.view":
                return "partials/financehome.html";
            case "/update.view":
            case "/ExpenseReimbursementSystem/update.view":
                return "partials/update.html";
            case "/updatereimb.view":
            case "/ExpenseReimbursementSystem/updatereimb.view":
                return "partials/updatereimb.html";
            default:
                return null;

        }


    }
}
