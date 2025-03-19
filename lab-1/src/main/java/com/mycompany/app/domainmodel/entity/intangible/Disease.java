package com.mycompany.app.domainmodel.entity.intangible;

public enum Disease {
    MYXOMATOSIS("myxomatosis"),
    UNKNOWN_DISEASE("unknown disease");

    private final String name;

    Disease(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
