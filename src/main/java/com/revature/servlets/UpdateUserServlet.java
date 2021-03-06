package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/updateuser")
public class UpdateUserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    /**
     * Handles requests to update a user.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        try {
            AppUser updatedUser = mapper.readValue(req.getInputStream(), AppUser.class);
            AppUser finalizedUser = userService.updateUser(updatedUser);
            System.out.println(finalizedUser);
            String updatedUserJSON = mapper.writeValueAsString(finalizedUser);
            respWriter.write(updatedUserJSON);
            resp.setStatus(201); // 201 = CREATED

        } catch (MismatchedInputException | InvalidRequestException mie) {
            mie.printStackTrace();
            resp.setStatus(400);

            ErrorResponse err = new ErrorResponse(400, "Bad Req: Malform reimb object found in request body");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (AuthenticatorException ae) {
            ae.printStackTrace();
            resp.setStatus(409);

            ErrorResponse err = new ErrorResponse(409, "This user does not exist, therefore cannot be updated.");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500); // 500 = INTERNAL SERVER ERROR

            ErrorResponse err = new ErrorResponse(500, "it's not you it's us :(");
            respWriter.write(mapper.writeValueAsString(err));
        }
    }
}
