package com.revature.services;

import com.revature.dtos.Principal;
import com.revature.exceptions.AuthenticatorException;
import com.revature.exceptions.InvalidInputException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.models.Reimb;
import com.revature.models.ReimbStatusTypes;
import com.revature.models.ReimbTypes;
import com.revature.repos.ReimbRepo;
import com.revature.util.ReimbComparator;

import java.util.ArrayList;
import java.util.Set;

public class ReimbService {

    public ReimbRepo reimbRepo = new ReimbRepo();


    public boolean addNewReimbursement(double amount, String description, String type, int id) {

        if (id <= 0 || amount <= 0 || description == null || description.trim().equals("") || type == null || type.trim().equals("")) {
            throw new InvalidInputException("Invalid credentials given for reimbursement.");
        } else if (amount > 99999.99) {
            throw new InvalidRequestException("Please keep your request between $0.01 and $99,999.99.");
        }

        int reimbType = ReimbStatusTypes.getIDFromName("type");

        reimbRepo.save(amount, description, reimbType, id);
        return true;
    }

    public Reimb updateReimb(double amount, String description, String reimb_type, int reimb_id, int loggedUserId) {

        Reimb reimb = reimbRepo.selectReimbursement(reimb_id);

        if (!isReimbValid(reimb)) {
            throw new InvalidInputException("Invalid credentials given for registration.");
        } else if (reimb.getAuthor_id() != loggedUserId) {
            throw new AuthenticatorException("This reimbursement does not belong to you.");
        } else if (reimb.getReimb_status() != ReimbStatusTypes.PENDING) {
            throw new InvalidRequestException("This reimbursement is not pending and can no longer be updated. Please submit a new one.");
        }

        int reimb_type_id = ReimbTypes.getIDFromName(reimb_type);
        reimbRepo.updateReimb(amount, description, reimb_type_id, reimb_id);
        return reimbRepo.selectReimbursement(reimb_id);

    }

    public Reimb updateReimbStatus(String status, int id, Principal currentFinMan) {

        Reimb reimb = reimbRepo.selectReimbursement(id);

        if (!isReimbValid(reimb)) {

            throw new InvalidInputException("Invalid credentials given for reimbursement.");
        }


        reimbRepo.updateReimbStatus(reimb, status, currentFinMan);
        return reimbRepo.selectReimbursement(reimb.getReimb_id());

    }

    public ArrayList<Reimb> getAllReimbs() {

        ArrayList<Reimb> reimbs = reimbRepo.findAllReimbs();

        if (reimbs.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        ArrayList<Reimb> list = new ArrayList<>(reimbs);
        list.sort(new ReimbComparator());

        return list;
    }

    public ArrayList<Reimb> getReimbsByType(String type) {

        ReimbTypes reimbType = ReimbTypes.getByName(type);

        ArrayList<Reimb> reimbs = reimbRepo.findReimbByType(reimbType);

        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        ArrayList<Reimb> list = new ArrayList<>(reimbs);
        list.sort(new ReimbComparator());


        return list;

    }

    public ArrayList<Reimb> getReimbByStatus(int id) {

        if (id <= 0) {
            throw new InvalidInputException("The provided Id cannot be less than or equal to 0!");
        }

        ArrayList<Reimb> reimbs = reimbRepo.findReimbByStatus(id);

        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        ArrayList<Reimb> list = new ArrayList<>(reimbs);
        list.sort(new ReimbComparator());


        return list;
    }

    public ArrayList<Reimb> getReimbsByUserId(int id) {

        if (id <= 0) {
            throw new InvalidInputException("The provided Id cannot be less than or equal to 0!");
        }

        ArrayList<Reimb> reimbs = reimbRepo.findReimbsByUser(id);
        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        ArrayList<Reimb> list = new ArrayList<>(reimbs);
        list.sort(new ReimbComparator());


        return list;
    }

    public ArrayList<Reimb> getReimbsByUserIdStatus(int id, String status) {

        if (id <= 0) {
            throw new InvalidInputException("The provided Id cannot be less than or equal to 0!");
        }

        ArrayList<Reimb> reimbs = reimbRepo.findReimbsByUserStatus(id, status);

        if (reimbs == null) {
            throw new ResourceNotFoundException();
        }

        ArrayList<Reimb> list = new ArrayList<>(reimbs);
        list.sort(new ReimbComparator());

        return list;
    }

    public boolean isReimbValid(Reimb reimb) {
        if (reimb == null) return false;
        if (reimb.getReimb_id() <= 0) return false;
        if (reimb.getAmount() <= 0) return false;
        if (reimb.getDescription() == null || reimb.getDescription().trim().equals("")) return false;
        if (reimb.getAuthor_id() <= 0) return false;
        if (reimb.getReimb_status() == null) return false;
        if (reimb.getReimb_type() == null) return false;
        return true;
    }


}
