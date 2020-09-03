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
            if(reimbType.roleName.equals(name)) {
                return reimbType;
            }
        }

        return OTHER;

    }

}
