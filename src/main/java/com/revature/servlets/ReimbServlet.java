package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidInputException;
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


@WebServlet("/reimbs/*")
public class ReimbServlet extends HttpServlet {
    private final ReimbService reimbService = new ReimbService();

    /**
     * Handles getting the reimbursements requested.
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


        try {
            if (principal.getRole().equalsIgnoreCase("employee")) {
                if (req.getParameter("status") != null) {
                    int id = principal.getId();
                    String status = req.getParameter("status");
                    ArrayList<Reimb> reimbs = reimbService.getReimbsByUserIdStatus(id, status);
                    respWriter.write(mapper.writeValueAsString(reimbs));
                } else {
                    int id = principal.getId();
                    ArrayList<Reimb> reimbs = reimbService.getReimbsByUserId(id);
                    respWriter.write(mapper.writeValueAsString(reimbs));
                }
            } else {
                if (!principal.getRole().equalsIgnoreCase("FinanceMan")) {
                    ErrorResponse err = new ErrorResponse(403, "Forbidden: your role does not permit you to access this method.");
                    respWriter.write(mapper.writeValueAsString(err));
                    resp.setStatus(403);
                    return;
                }

                ArrayList<Reimb> reimbs = reimbService.getAllReimbs();
                String reimbsJSON = mapper.writeValueAsString(reimbs);
                respWriter.write(reimbsJSON);
            }
            resp.setStatus(200);
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

    /**
     * Used to handle incoming requests to register new reimbursements for the application.
     *
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

        String principalJSON = (String) req.getSession().getAttribute("principal");


        if (principalJSON == null) {
            ErrorResponse err = new ErrorResponse(401, "No principal object found on request.");
            respWriter.write(mapper.writeValueAsString(err));
            resp.setStatus(401);
            return; //make sure it doesnt fall through
        }

        Principal principal = mapper.readValue(principalJSON, Principal.class);

        try {
            int id = principal.getId();
            System.out.println(req.getParameter("amount"));
            double amount = Double.parseDouble(req.getParameter("amount"));
            String type = req.getParameter("type");
            String description = req.getParameter("description");

            reimbService.addNewReimbursement(amount, description, type, id);
            resp.setStatus(201); // 201 = CREATED

        } catch (InvalidRequestException mie) {
            mie.printStackTrace();
            resp.setStatus(403);

            ErrorResponse err = new ErrorResponse(403, "Please keep your request between $0.01 and $9,999.99.");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (InvalidInputException iie) {
            iie.printStackTrace();
            resp.setStatus(400);

            ErrorResponse err = new ErrorResponse(400, "Bad Req: Malform reimb object found in request body");
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