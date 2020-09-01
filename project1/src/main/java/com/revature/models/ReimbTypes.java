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
}
