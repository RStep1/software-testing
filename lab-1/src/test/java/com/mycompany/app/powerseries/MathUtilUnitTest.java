package com.mycompany.app.powerseries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import com.mycompany.app.util.MathUtil;


public class MathUtilUnitTest {
    @Test
    public void givenSmallNumber_whenCalculateFactorial_thenReturnResult() {
        long actualValue = MathUtil.factorial(3);
        long expectedValue = 6;
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenMinimalNumber_whenCalculateFactorial_thenReturnResult() {
        long actualValue = MathUtil.factorial(0);
        long expectedValue = 1;
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    public void givenLargeNumber_whenCalculateFactorial_thenReturnResult() {
        long actualValue = MathUtil.factorial(20);
        long expectedValue = 2432902008176640000L;
        Assertions.assertEquals(expectedValue, actualValue);
    }

}
