package com.mycompany.app.domainmodel.experiments.experimentslib;

import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OtherExperiments {
    public static Result forceToRun(Mouse mouse, Place place) {
        log.info(String.format("%s are running in %s", mouse, place));
        return new Result(String.format("%s were forced to run in %s", mouse, place), true);
    }
}
