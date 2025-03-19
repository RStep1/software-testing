package com.mycompany.app.domainmodel.experiments.experimentslib;

import com.mycompany.app.domainmodel.experiments.Result;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tests {
    public static Result test1(Mouse mouse) {
        log.info(String.format("Test #1: mouse %d", mouse.hashCode()));
        return new Result(String.format("Success test1 on mouse %d", mouse.hashCode()), true);
    }

    public static Result test2(Mouse mouse) {
        log.info(String.format("Test #2: mouse %d", mouse.hashCode()));
        return new Result(String.format("Success test2 on mouse %d", mouse.hashCode()), true);
    }
}
