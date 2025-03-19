package com.mycompany.app.domainmodel.memento;

import java.util.Date;

public record History(Date date, String circumstances) {
    public History(String circumstances) {
        this(null, circumstances);
    }
}
