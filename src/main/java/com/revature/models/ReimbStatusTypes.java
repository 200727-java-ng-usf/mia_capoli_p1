package com.revature.models;

/**
 * An enum for different status types.
 */
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
            if (reimbSType.roleName.equalsIgnoreCase(name)) {
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

        if (name.equalsIgnoreCase("Pending")) {
            return 1;
        } else if (name.equalsIgnoreCase("Approved")) {
            return 2;
        } else {
            return 3;
        }

    }


}
