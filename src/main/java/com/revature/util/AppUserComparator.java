package com.revature.util;
import com.revature.models.AppUser;

import java.util.Comparator;

public class AppUserComparator implements Comparator<AppUser>{

    @Override
    public int compare(AppUser o1, AppUser o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
