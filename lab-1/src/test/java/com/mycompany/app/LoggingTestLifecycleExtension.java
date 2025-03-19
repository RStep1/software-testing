package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingTestLifecycleExtension implements BeforeEachCallback, AfterEachCallback, TestWatcher,
                                                      AfterAllCallback {
    private final List<TestResultStatus> testResultsStatus = new ArrayList<>();
    
    private enum TestResultStatus {
        SUCCESSFUL, FAILED, ABORTED, DISABLED;
    }

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> reason) {
        log.info("Test Disabled for test {}: with reason: {}",
            extensionContext.getDisplayName(),
            reason.orElse("No reason"));
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.info("Test Aborted for test {}\n", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.info("Test Failed for test {}\n", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.FAILED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("Tes Successful for test {}\n", context.getDisplayName());
        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }
    
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        log.info("Running test {}", context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        log.info("Finishing test {}", context.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        Map<TestResultStatus, Long> summery = testResultsStatus.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        log.info("Test result summery for {} {}", context.getDisplayName(), summery.toString());
    }
}
