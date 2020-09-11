package com.revature.services;

import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimb;
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
        //if the user isn't valid, invalidate them and throw an exception.
        if (!isReimbValid(reimb)) {
            throw new InvalidInputException("Invalid credentials given for reimbursement.");
        }
        //TODO get currently logged in user
//        if (user.get().getRole() != Role.ADMIN) {
//            app.invalidateCurrentUser();
//            throw new AuthenticatorException("You aren't allowed to update any users!");
//            //to  do return home
//        }

        reimbRepo.save(reimb);
    }



    public void updateReimb(int reimb_id, int amount, Timestamp submitted, Timestamp resolved,
                            String description, String receipt, int author_id,
                            int resolver_id, int reimb_status_id, int reimb_type_id) {
        //if the user isn't valid, invalidate them and throw an exception.

        Reimb reimb = reimbRepo.selectReimbursement(reimb_id);

        if (!isReimbValid(reimb)) {

            throw new InvalidInputException("Invalid credentials given for registration.");
        }
        //TODO get currently logged n user
//        if (user.get().getRole() != Role.ADMIN) {
//            app.invalidateCurrentUser();
//            throw new AuthenticatorException("You aren't allowed to update any users!");
//            //to  do return home
//        }

        reimb = new Reimb(amount, submitted, resolved,
                description, receipt, author_id,
        resolver_id, reimb_status_id, reimb_type_id);
        reimbRepo.updateReimb(reimb);

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


        //todo check if finance manager

        ReimbTypes reimbType = ReimbTypes.getByName(type);

        Set<Reimb> reimbs =  reimbRepo.findReimbByType(reimbType);

        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        return reimbs;

    }

    public Set<Reimb> getReimbByStatus(int id) {


        //todo check if finance manager
        if (id <= 0) {
            throw new InvalidRequestException("The provided Id cannot be less than or equal to 0!");
        }

        Set<Reimb> reimbs = reimbRepo.findReimbByStatus(id);

             if (reimbs == null) {
                 throw new ResourceNotFoundException();
             }
        return reimbs;
    }

    public Reimb getReimbByUserId(int id) {

        if (id <= 0) {
            throw new InvalidRequestException("The provided Id cannot be less than or equal to 0!");
        }

        return reimbRepo.selectReimbursement(id);
    }

    //todo check if times are valid??
    public boolean isReimbValid(Reimb reimb) {
        if (reimb == null) return false;
        if (reimb.getReimb_id() <= 0 ) return false;
        if (reimb.getAmount() <= 0) return false;
//        if (reimb.getSubmitted() > Time.now) return false;
//        if (reimb.getResolved() > Time.now) return false;
        if (reimb.getDescription() == null || reimb.getDescription().trim().equals("")) return false;
        if (reimb.getReceipt() == null || reimb.getReceipt().trim().equals("")) return false;
        if (reimb.getAuthor_id() <= 0 ) return false;
        if (reimb.getResolver_id() <= 0 ) return false;
        if (reimb.getReimb_status_id() <= 0 ) return false;
        if (reimb.getReimb_type() != ReimbTypes.FOOD || reimb.getReimb_type() != ReimbTypes.OTHER ||  reimb.getReimb_type() != ReimbTypes.LODGING || reimb.getReimb_type() != ReimbTypes.TRAVEL ) return false;
        return true;
    }


}
