package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ValidationHelper {

    private final UserService userService = new UserService();

    /**
     * Helps with validating emails and usernames.
     * @param req
     * @return
     * @throws IOException
     */
    public boolean process(HttpServletRequest req) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        switch (req.getRequestURI()) {
            case "/email.validate":
            case "/ExpenseReimbursementSystem/email.validate":
                String email = mapper.readValue(req.getInputStream(), String.class);
                return userService.isEmailAvailable(email);
            case "/username.validate":
            case "/ExpenseReimbursementSystem/username.validate":
                String username = mapper.readValue(req.getInputStream(), String.class);
                return userService.isUsernameAvailable(username);
            default:
                return false;
        }

    }
}