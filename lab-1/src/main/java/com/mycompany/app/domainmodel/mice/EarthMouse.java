package com.mycompany.app.domainmodel.mice;

import java.util.List;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.entity.animate.Entity;
import com.mycompany.app.domainmodel.entity.intangible.Disease;
import com.mycompany.app.domainmodel.entity.intangible.Message;
import com.mycompany.app.domainmodel.memento.History;

import lombok.extern.slf4j.Slf4j;

/*
 * This class represents proxy service.
 */
@Slf4j
public class EarthMouse implements Mouse {
    private Mouse mouse;
    private static final String description = "This is just a projection onto our dimension of huge hyper-intelligent omnidimensional beings.";

    public EarthMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    @Override
    public int ringBells() {
        log.info(String.format("%s ring bells", this.toString()));
        return mouse.ringBells();
    }
    
    @Override
    public int run(Place where, boolean isBehaviorExpecting) {
        if (isBehaviorExpecting) {
            log.info(String.format("Mouse %d is running in %s", this.hashCode(), where.getName()));
        } else {
            log.info(String.format("Mouse %d is running in %s in the wrong way", this.hashCode(), where.getName()));
        }
        return mouse.run(where, isBehaviorExpecting);
    }
    
    @Override
    public int eat(Meal meal, boolean isBehaviorExpecting) {
        if (isBehaviorExpecting) {
            log.info(String.format("Mouse %d is eating the %s", this.hashCode(), meal.getName()));
        } else {
            log.info(String.format("Mouse %d is eating the wrong bit of %s", this.hashCode(), meal.getName()));
        }
        return mouse.eat(meal, isBehaviorExpecting);
    }

    @Override
    public int die(Disease disease) {
        log.info("Mouse %d died from %s", this.hashCode(), disease.getName());
        return mouse.die(disease);
    }

    @Override
    public History figureOut(List<Message> messages) {
        return mouse.figureOut(messages);
    }
    
    @Override
    public History hit(Entity entity) {
        return mouse.hit(entity);
    }

    @Override
    public History runAway() {
        return mouse.runAway();
    }

    @Override
    public String toString() {
        return "small animals with white fur";
    }

    public String getDescription() {
        return description;
    }

}
