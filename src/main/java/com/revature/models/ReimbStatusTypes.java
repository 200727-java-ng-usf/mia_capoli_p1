package com.revature.models;

public enum ReimbStatusTypes {

    PENDING("Pending"),
    APPROVED("Approved"),
    DENIED("Denied");

    private String roleName;

    ReimbStatusTypes(String name) {
        this.roleName = name;
    }

    public static ReimbStatusTypes getByName(String name) {

        for (ReimbStatusTypes reimbSType : ReimbStatusTypes.values()) {
            if (reimbSType.roleName.equals(name)) {
                return reimbSType;
            }
        }

        return DENIED;

    }

    public static ReimbStatusTypes getByID(int id) {

        for (ReimbStatusTypes reimbSType : ReimbStatusTypes.values()) {
            if (reimbSType.ordinal() - 1 == id) {
                return reimbSType;
            }
        }

        return null;

    }


    public static int getIDFromName(String name) {

        ReimbStatusTypes currentReimb = ReimbStatusTypes.getByName(name);

        if (currentReimb.ordinal() == 1) {
            return 1;
        } else if (currentReimb.ordinal() == 2) {
            return 2;
        } else {
            return 3;
        }

    }


}
