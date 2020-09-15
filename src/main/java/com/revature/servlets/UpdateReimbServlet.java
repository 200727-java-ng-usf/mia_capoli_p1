package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.AppUser;
import com.revature.models.Reimb;
import com.revature.services.ReimbService;
import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@WebServlet("/updatereimb")
public class UpdateReimbServlet extends HttpServlet {
    private final ReimbService reimbService = new ReimbService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter respWriter = resp.getWriter();
        try {
            String principalJSON = (String) req.getSession().getAttribute("principal");


            Principal principal = mapper.readValue(principalJSON, Principal.class);

            if (principal.getRole().equalsIgnoreCase("FinanceMan")) {
                if (req.getParameter("status") != null) {
                    String status = req.getParameter("status");
                    String idString = req.getParameter("id");
                    System.out.println(idString);
                    int id = Integer.parseInt(idString);

                    Reimb reimb = reimbService.updateReimbStatus(status, id, principal);
                    String reimbJSON = mapper.writeValueAsString(reimb);
                    respWriter.write(reimbJSON);
                }
            }

            if (principal.getRole().equalsIgnoreCase("Employee")) {
                int selectedReimbID = Integer.parseInt(req.getParameter("id"));
                int amount = Integer.parseInt(req.getParameter("amount"));
                String reimbType = req.getParameter("type");
                String description = req.getParameter("description");

                Reimb finalizedReimb = reimbService.updateReimb(amount, description, reimbType, selectedReimbID);
                String updatedUserJSON = mapper.writeValueAsString(finalizedReimb);
                respWriter.write(updatedUserJSON);
                resp.setStatus(201); // 201 = CREATED
            }
        } catch (MismatchedInputException | InvalidRequestException mie) {
            mie.printStackTrace();
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
