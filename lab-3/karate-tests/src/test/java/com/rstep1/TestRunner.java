package com.rstep1;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import io.qameta.allure.karate.AllureKarate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestRunner {
    
    @BeforeAll
    static void setup() {
        TestContainersSetup.startContainers();
    }
    
    @Test
    void testAll() {
        Results results = Runner.path("classpath:karate/features")
                .hook(new AllureKarate())
                .outputCucumberJson(true)
                .outputJunitXml(true)
                .parallel(1);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
    
    @AfterAll
    static void tearDown() {
        TestContainersSetup.stopContainers();
    }
}