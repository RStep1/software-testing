package com.mycompany.app.domainmodel.mice;

import java.util.List;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.entity.animate.Entity;
import com.mycompany.app.domainmodel.entity.animate.ExpressionType;
import com.mycompany.app.domainmodel.entity.intangible.Disease;
import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.memento.History;

import lombok.extern.slf4j.Slf4j;

/*
 * This class represents a real service.
 */
@Slf4j
public class OmnidimensionalCreature implements Mouse {
    
    @Override
    public int ringBells() {
        return 10;
    }

    @Override
    public int run(Place where, boolean isBehaviorExpecting) {
        if (isBehaviorExpecting)
            return 15;
        else
            return 25;
    }

    @Override
    public int eat(Meal meal, boolean isBehaviorExpecting) {
        if (isBehaviorExpecting)
            return 8;
        else
            return 20;
    }

    @Override
    public int die(Disease disease) {
        log.info(this.toString() + "still alive actually. Only its projections died.");
        return 5;
    }
    
    @Override
    public History figureOut(List<Message> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" figuring out ");

        boolean consistQuestion = false;
        for (int i = 0; i < messages.size(); i++) {
            if (messages.get(i).getExpresstionType() == ExpressionType.QUESTION) {
                consistQuestion = true;
            }
        }
        if (consistQuestion) {
            stringBuilder.append("questions ");
        } else {
            stringBuilder.append("problems ");
        }
        stringBuilder.append("once and for all.");

        log.info(stringBuilder.toString());
        return new History(stringBuilder.toString());
    }

    @Override
    public History hit(Entity entity) {
        String description = this.toString() + " hit the " + entity.getClass().getName();
        log.info(description);
        return new History(description);
    }

    @Override
    public History runAway() {
        String description = this.toString() + " running away";
        log.info(description);
        return new History(description);
    }

    @Override
    public String toString() {
        return "huge hyper-intelligent omnidimensional creatures";
    }

    @Override
    public String getDescription() {
        return this.toString();
    }
}
