package com.revature.services;

import com.revature.dtos.Principal;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimb;
import com.revature.models.ReimbStatusTypes;
import com.revature.models.ReimbTypes;
import com.revature.repos.ReimbRepo;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Set;

public class ReimbService {

    //todo select reimb
    //todo update reimb + give fields- if approve / deny, check if finance manager, update if in pending state


    private final ReimbRepo reimbRepo = new ReimbRepo();


    public void addNewReimbursement(Reimb reimb) {

        if (!isReimbValid(reimb)) {
            throw new InvalidInputException("Invalid credentials given for reimbursement.");
        }

        reimbRepo.save(reimb);
    }



    public Reimb updateReimb(int amount, String description, String reimb_type, int reimb_id) {


        Reimb reimb = reimbRepo.selectReimbursement(reimb_id);

        if (!isReimbValid(reimb)) {

            throw new InvalidInputException("Invalid credentials given for registration.");
        }

        int reimb_type_id = ReimbTypes.getIDFromName(reimb_type);
        reimbRepo.updateReimb(amount, description, reimb_type_id, reimb_id);
        return reimbRepo.selectReimbursement(reimb_id);

    }

    public Reimb updateReimbStatus(String status, int id, Principal currentFinMan) {


        Reimb reimb = reimbRepo.selectReimbursement(id);

        if (!isReimbValid(reimb)) {

            throw new InvalidInputException("Invalid credentials given for registration.");
        }


        reimbRepo.updateReimbStatus(reimb, status, currentFinMan);
        return reimbRepo.selectReimbursement(reimb.getReimb_id());

    }

    public Set<Reimb> getAllReimbs() {

        Set<Reimb> reimbs = reimbRepo.findAllReimbs();

        //todo check if finance manager
        //else get all by user id

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return reimbs;
    }

    public Set<Reimb> getReimbsByType(String type) {

        ReimbTypes reimbType = ReimbTypes.getByName(type);

        System.out.println(reimbType);

        Set<Reimb> reimbs =  reimbRepo.findReimbByType(reimbType);

        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    public Set<Reimb> getReimbByStatus(int id) {


        if (id <= 0) {
            throw new InvalidRequestException("The provided Id cannot be less than or equal to 0!");
        }

        Set<Reimb> reimbs = reimbRepo.findReimbByStatus(id);

             if (reimbs == null) {
                 throw new ResourceNotFoundException();
             }
        return reimbs;
    }

    public Set<Reimb> getReimbsByUserId(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("The provided Id cannot be less than or equal to 0!");
        }

        return reimbRepo.findReimbsByUser(id);
    }

    public boolean isReimbValid(Reimb reimb) {
        if (reimb == null) return false;
        if (reimb.getReimb_id() <= 0 ) return false;
        if (reimb.getAmount() <= 0) return false;
        if (reimb.getDescription() == null || reimb.getDescription().trim().equals("")) return false;
        if (reimb.getAuthor_id() <= 0 ) return false;
        if (reimb.getReimb_status() == null) return false;
        if (reimb.getReimb_type() == null ) return false;
        return true;
    }


}
