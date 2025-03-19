package com.mycompany.app.domainmodel.experiments.experimentslib;

import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pavlov {
    public static Result makePavlovExperiment(Mouse mouse) {
        log.info(String.format("Making Pavlov experiment on mouse %d", mouse.hashCode()));
        return new Result(String.format("Mouse %d salivates in the light of a lamp", mouse.hashCode()), true);
    }
}
