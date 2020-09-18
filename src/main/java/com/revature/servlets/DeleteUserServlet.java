package com.revature.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.ErrorResponse;
import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteuser")
public class DeleteUserServlet extends HttpServlet {
    private final UserService userService = new UserService();

    /**
     * Delete a user.
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
        try {

            String principalJSON = (String) req.getSession().getAttribute("principal");

            if (principalJSON == null) {
                ErrorResponse err = new ErrorResponse(401, "No principal object found on request.");
                respWriter.write(mapper.writeValueAsString(err));
                resp.setStatus(401);
                return; //make sure it doesnt fall through
            }

            Principal principal = mapper.readValue(principalJSON, Principal.class);

            if (!principal.getRole().equalsIgnoreCase("Admin")) {
                ErrorResponse err = new ErrorResponse(403, "Forbidden: your role does not permit you to access this endpoint.");
                respWriter.write(mapper.writeValueAsString(err));
                resp.setStatus(403);
                return;
            }

            String deletedUserId = req.getParameter("id");
            System.out.println(deletedUserId);
            if (deletedUserId != "null") {
                int id = Integer.parseInt(deletedUserId);
                if (id == principal.getId()) {
                    ErrorResponse err = new ErrorResponse(403, "Forbidden: You cannot delete yourself...");
                    respWriter.write(mapper.writeValueAsString(err));
                    resp.setStatus(403);
                    return;
                }
                userService.deleteUser(id);
                resp.setStatus(204);
            } else {
                ErrorResponse err = new ErrorResponse(404, "No user with such id found.");
                String errJSON = mapper.writeValueAsString(err);
                respWriter.write(errJSON);
            }


        } catch (NumberFormatException | InvalidRequestException mie) {
            mie.printStackTrace();
            resp.setStatus(400);

            ErrorResponse err = new ErrorResponse(400, "Bad Req: Malform int object found in request body");
            String errJSON = mapper.writeValueAsString(err);
            respWriter.write(errJSON);
        } catch (InvalidInputException iie) {
            iie.printStackTrace();
            resp.setStatus(409);

            ErrorResponse err = new ErrorResponse(400, "This user does not exist, therefore cannot be deleted.");
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
