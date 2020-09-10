package com.revature.util;

import com.revature.controllers.AdminHomeController;
import com.revature.controllers.RegisterController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RequestViewHelper {

    public String process(HttpServletRequest req) throws IOException {
        switch (req.getRequestURI()) {

            case "/ExpenseReimbursementSystem/login.view":
                return "partials/login.html";
            case "/ExpenseReimbursementSystem/home.view":
                System.out.println("in home case");
                return AdminHomeController.home(req);
            case "/ExpenseReimbursementSystem/api/register":
                System.out.println("in register case");
                return RegisterController.registerNewUser(req);
            case "/ExpenseReimbursementSystem/api/updateuser":
                System.out.println("in updateuser case");
//				return AdminHomeController.registerNewUser(req);
            default:
                System.out.println("in default");
                return "/html/badlogin.html";

        }



    }
}
