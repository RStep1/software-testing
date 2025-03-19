package com.mycompany.app.domainmodel.mice;

import com.mycompany.app.domainmodel.entity.animate.Entity;
import com.mycompany.app.domainmodel.memento.History;

public interface BrockianUltraCricket {
    public static final String DESCRIPTION = "a curious game";
    History hit(Entity entity);
    History runAway();
}
