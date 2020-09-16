package com.revature.util;
import com.revature.models.Reimb;

import java.util.Comparator;

public class ReimbComparator implements Comparator<Reimb>{

    @Override
    public int compare(Reimb o1, Reimb o2) {
        return Integer.compare(o1.getReimb_id(), o2.getReimb_id());
    }
}
