package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimb;
import com.revature.services.ReimbService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;


@WebServlet("/reimbspending/*")
public class ReimbPendingServlet extends HttpServlet {
    private final ReimbService reimbService = new ReimbService();

    /**
     * Get the pending reimbs.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        resp.setContentType("application/json");

        String principalJSON = (String) req.getSession().getAttribute("principal");


        if (principalJSON == null) {
            ErrorResponse err = new ErrorResponse(401, "No principal object found on request.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(401);
            return; //make sure it doesnt fall through
        }

        Principal principal = mapper.readValue(principalJSON, Principal.class);

        if (!principal.getRole().equalsIgnoreCase("FinanceMan")) {
            ErrorResponse err = new ErrorResponse(403, "Forbidden: your role does not permit you to access this endpoint.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(403);
            return;
        }



        try {
            ArrayList<Reimb> reimbs = reimbService.getReimbByStatus(1);
            String usersJSON = mapper.writeValueAsString(reimbs);
            respWriter.write(usersJSON);
            resp.setStatus(200); //not req, 200 by default if no exceptions /errors are thrown
        } catch (ResourceNotFoundException rnfe) {

            resp.setStatus(404); //not found!!!
            ErrorResponse err = new ErrorResponse(404, rnfe.getMessage());
            respWriter.write(mapper.writeValueAsString(err));
        } catch (NumberFormatException | InvalidRequestException e) {

            resp.setStatus(400);
            ErrorResponse err = new ErrorResponse(500, "Malformed user id parameter value provided.");
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