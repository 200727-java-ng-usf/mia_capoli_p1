package com.revature.models;

public enum ReimbTypes {

    LODGING("Lodging"),
    TRAVEL("Travel"),
    FOOD("Food"),
    OTHER("Other");

    private String roleName;

    ReimbTypes(String name) {
        this.roleName = name;
    }

    public static ReimbTypes getByName(String name) {

        for (ReimbTypes reimbType : ReimbTypes.values()) {
            if (reimbType.roleName.equals(name)) {
                return reimbType;
            }
        }

        return OTHER;

    }

    public static ReimbTypes getByID(int id) {

        for (ReimbTypes reimbType : ReimbTypes.values()) {
            if (reimbType.ordinal() - 1 == id) {
                return reimbType;
            }
        }

        return null;

    }


    public static int getIDFromName(String name) {

        ReimbTypes currentReimb = ReimbTypes.getByName(name);

        if (currentReimb.ordinal() == 1) {
            return 1;
        } else if (currentReimb.ordinal() == 2) {
            return 2;
        } else if (currentReimb.ordinal() == 3) {
            return 3;
        } else {
            return 4;
        }

    }


}
