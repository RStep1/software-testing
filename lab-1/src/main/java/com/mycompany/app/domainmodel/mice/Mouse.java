package com.mycompany.app.domainmodel.mice;

import java.util.List;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.entity.intangible.Disease;
import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.memento.History;

/*
 * This is an interface for service and proxy.
 */
public interface Mouse extends BrockianUltraCricket {
    int ringBells();
    int run(Place where, boolean isBehaviorExpecting);
    int eat(Meal meal, boolean isBehaviorExpecting);
    int die(Disease disease);
    History figureOut(List<Message> messages);

    String getDescription();
}
