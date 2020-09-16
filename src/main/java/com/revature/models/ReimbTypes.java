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
            if (reimbType.roleName.equalsIgnoreCase(name)) {
                System.out.println("get by name=" + reimbType);
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

        if (name.equalsIgnoreCase("Lodging")) {
            return 1;
        } else if (name.equalsIgnoreCase("Travel")) {
            return 2;
        } else if (name.equalsIgnoreCase("Food")) {
            return 3;
        } else {
            return 4;
        }

    }




}
