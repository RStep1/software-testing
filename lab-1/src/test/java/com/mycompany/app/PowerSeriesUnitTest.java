package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.mycompany.app.powerseries.PowerSeries;

public class PowerSeriesUnitTest {
    static List<Double> eighteenExpectedTanCoefficients;
    final List<Double> expectedCoefficients = new ArrayList<>();

    @BeforeAll
    public static void setUpTanCoefficients() {
        eighteenExpectedTanCoefficients = Arrays.asList(1D, 0D, 1D / 3D, 0D, 2D / 15D, 0D, 17D / 315D, 0D,
            62D / 2835D, 0D, 1382D / 155925D, 0D, 21844D / 6081075D, 0D, 929569D / 638512875D, 0D,
            6404582D / 10854718875D, 0D);
    }

    @Test
    public void givenFewTerms_whenExecuteTanExpansion_thenReturnCoefficients() {
        final int countOfCoefficients = 3;
        final int roundSignNumber = 5;
        final double delta = 1 / Math.pow(10, roundSignNumber);

        List<Double> actualCoefficients = PowerSeries.getTanExpansionCoefficients(countOfCoefficients, roundSignNumber);

        expectedCoefficients.addAll(eighteenExpectedTanCoefficients.subList(0, countOfCoefficients));

        Assertions.assertAll(
            () -> Assertions.assertEquals(expectedCoefficients.size(), actualCoefficients.size(),
                "The size of coefficient lists should be equal"),
            () -> {
                for (int i = 0; i < expectedCoefficients.size(); i++) {
                    Assertions.assertEquals(expectedCoefficients.get(i), actualCoefficients.get(i), delta,
                        String.format("Coefficient at index %d does not match", i));
                }
            }
        );
    }

    @Disabled
    @Test
    public void givenMinimalTerms_whenExecuteTanExpansion_thenReturnCoefficients() {
        
    }

    @AfterEach
    public void cleanUpActualCoefficients() {
        expectedCoefficients.clear();
    }
}
