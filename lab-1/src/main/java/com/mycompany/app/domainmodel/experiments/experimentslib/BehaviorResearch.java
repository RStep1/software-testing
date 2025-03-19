package com.mycompany.app.domainmodel.experiments.experimentslib;

import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BehaviorResearch {
    public static Result findExitFrom(Mouse mouse, Place place) throws IllegalArgumentException {
        if (place == Place.SOMEWHERE) {
            throw new IllegalArgumentException(String.format("Mouse cannot find exit from %s", place.getName()));
        }
        log.info(String.format("Mouse %d is finding exit", mouse.hashCode()));
        mouse.run(place, true);
        return new Result(String.format("%s %d successfully find exit from", mouse, mouse.hashCode()), true);
    }
}
