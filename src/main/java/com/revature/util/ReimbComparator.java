package com.revature.util;
import com.revature.models.Reimb;

import java.util.Comparator;

public class ReimbComparator implements Comparator<Reimb>{

    /**
     * Compares reimbursements for sorting.
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(Reimb o1, Reimb o2) {
        return Integer.compare(o1.getReimb_id(), o2.getReimb_id());
    }
}
