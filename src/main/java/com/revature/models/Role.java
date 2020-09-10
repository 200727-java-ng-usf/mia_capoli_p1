package com.revature.models;

public enum Role {

// values declared within enums are constants and are comma separated
    EMPLOYEE("Employee"),
    ADMIN("Admin"),
    FINANCE_MANAGER("FinanceMan");

    private String roleName;

    Role(String Name) {
        this.roleName = Name;
    }

    public static Role getByName(String name) {

        for (Role role : Role.values()) {
            if(role.roleName.equals(name)) {
                return role;
            }
        }

        return null;

    }

    public static int getIDFromName(String name) {

        Role currentRole = Role.getByName(name);

        if (currentRole.ordinal() == 1) {
            return 1;
        } else if (currentRole.ordinal() == 2) {
            return 2;
        } else {
            return 3;
        }

    }

    public static Role getByID(int id) {

        for (Role role : Role.values()) {
            if(role.ordinal() - 1 == id) {
                return role;
            }
        }

        return null;

    }



    @Override
    public String toString() {
        return roleName;
    }
}
