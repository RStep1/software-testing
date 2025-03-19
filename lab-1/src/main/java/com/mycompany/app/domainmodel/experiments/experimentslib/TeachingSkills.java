package com.mycompany.app.domainmodel.experiments.experimentslib;

import com.mycompany.app.domainmodel.entity.Meal;
import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeachingSkills {
    public static Result teachRingTheBells(Mouse mouse) {
        log.info(String.format("%s %d is in process of learning how to ring the bells", mouse.toString(), mouse.hashCode()));
        mouse.ringBells();
        return new Result(String.format("%s %d was taught to ring the bells.", mouse.toString(), mouse.hashCode()), true); 
    }

    public static Result findMealIn(Mouse mouse, Place place, Meal meal) throws IllegalArgumentException {
        if (meal == Meal.UNKNOWN_MEAL) {
            throw new IllegalArgumentException(String.format("Mouse cannot find %s", meal.toString()));
        }
        log.info(String.format("%s %d is in process of learning how to find %s in %s",
            mouse.toString(), mouse.hashCode(), meal.getName(), place.getName()));
        mouse.eat(meal, true);
        return new Result(String.format("%s %d was taught to ring the bells.", mouse.toString(), mouse.hashCode()), true); 
    }
}
