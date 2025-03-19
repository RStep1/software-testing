package com.mycompany.app.domainmodel.entity;

public enum Meal {
    CHEESE("cheese"),
    UNKNOWN_MEAL("unknown meal");

    private final String name;

    Meal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
