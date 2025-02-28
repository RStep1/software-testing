package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.lang.IllegalArgumentException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mycompany.app.powerseries.PowerSeries;

public class PowerSeriesUnitTest {
    private static List<Double> eighteenExpectedTanCoefficients;
    private final List<Double> expectedCoefficients = new ArrayList<>();
    private int countOfCoefficients;
    private int roundSignNumber;
    private double delta;

    private double calculateDelta(int precision) {
        return 1 / Math.pow(10, precision);
    }

    @BeforeAll
    public static void setUpTanCoefficients() {
        eighteenExpectedTanCoefficients = Arrays.asList(1D, 0D, 1D / 3D, 0D, 2D / 15D, 0D, 17D / 315D, 0D,
            62D / 2835D, 0D, 1382D / 155925D, 0D, 21844D / 6081075D, 0D, 929569D / 638512875D, 0D,
            6404582D / 10854718875D, 0D);
    }

    @Test
    public void givenFewCoefficients_whenExecuteTanExpansion_thenReturnCoefficients() {
        countOfCoefficients = 3;
        roundSignNumber = 5;
        delta = calculateDelta(roundSignNumber);

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

    @Test
    public void givenMinimalCoefficients_whenExecuteTanExpansion_thenReturnCoefficients() {
        countOfCoefficients = 1;
        roundSignNumber = 6;
        delta = calculateDelta(roundSignNumber);

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

    @Test
    public void givenMaximalCoefficients_whenExecuteTanExpansion_thenReturnCoefficients() {
        countOfCoefficients = 18;
        roundSignNumber = 8;
        delta = calculateDelta(roundSignNumber);

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

    @Test
    public void givenHighPrecision_whenExecuteTanExpansion_thenReturnCoefficients() {
        countOfCoefficients = 10;
        roundSignNumber = 10;
        delta = calculateDelta(roundSignNumber);

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

    @Test
    public void givenLowPrecision_whenExecuteTanExpansion_thenReturnCoefficients() {
        countOfCoefficients = 11;
        roundSignNumber = 2;
        delta = calculateDelta(roundSignNumber);

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

    @Test
    public void givenIncorrectPrecision_whenExecuteTanExpansion_thenThrowsException() {
        countOfCoefficients = 5;
        roundSignNumber = -1;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PowerSeries.getTanExpansionCoefficients(countOfCoefficients, roundSignNumber);
        }); 
    }

    @Test
    public void givenIncorrectCoefficientCount_whenExecuteTanExpansion_thenThrowsException() {
        countOfCoefficients = 40;
        roundSignNumber = 3;

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PowerSeries.getTanExpansionCoefficients(countOfCoefficients, roundSignNumber);
        });
    }

    @AfterEach
    public void cleanUpExpectedCoefficients() {
        expectedCoefficients.clear();
    }
}
