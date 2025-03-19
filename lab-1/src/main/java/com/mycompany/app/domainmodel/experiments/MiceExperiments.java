package com.mycompany.app.domainmodel.experiments;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.app.domainmodel.entity.Place;
import com.mycompany.app.domainmodel.experiments.experimentslib.BehaviorResearch;
import com.mycompany.app.domainmodel.experiments.experimentslib.Pavlov;
import com.mycompany.app.domainmodel.experiments.experimentslib.TeachingSkills;
import com.mycompany.app.domainmodel.experiments.experimentslib.Tests;
import com.mycompany.app.domainmodel.mice.Mouse;

import lombok.extern.slf4j.Slf4j;

/*
 * This class serves as facade for all experiments on mice.
 */
@Slf4j
public class MiceExperiments {
    
    public static Result behaviorResearch(Mouse mouse, Place place) {
        log.info("Preparing for behavior research");
        return BehaviorResearch.findExitFrom(mouse, place);
    }

    public static List<Result> makeTests(Mouse mouse) {
        List<Result> results = new ArrayList<>();
        log.info("Preparing for some tests");
        results.add(Tests.test1(mouse));
        results.add(Tests.test2(mouse));
        return results;
    }

    public static Result makePavlovExperiment(Mouse mouse) {
        log.info("Preparing for Pavlov experiment");
        return Pavlov.makePavlovExperiment(mouse);
    }

    public static Result teachMouse(Mouse mouse) {
        log.info("Preparing for teaching");
        return TeachingSkills.teachRingTheBells(mouse);
    }
}
