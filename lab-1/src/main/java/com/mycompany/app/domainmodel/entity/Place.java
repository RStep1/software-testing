package com.mycompany.app.domainmodel.entity;

public enum Place {
    MAZE("maze"),
    SOMEWHERE("somewhere");

    private final String name;

    Place(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
