package com.revature.screens;

/**
 * Screen Abstract Class with constructor and render abstract method, for screens to base themselves off of.
 */
public abstract class Screen {
    private String name;
    private String location;

    protected Screen(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public abstract void render();

}
