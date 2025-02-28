package com.mycompany.app.powerseries;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Precision;

import com.mycompany.app.util.MathUtil;

public class PowerSeries {
    
    private static final double ZERO_COEFFICIENT = 0.0;
    private static final int MAX_COEFFICIENT_COUNT = 18;
    private static final int MIN_COEFFICIENT_COUNT = 1;
    private static final int MAX_PRECISION = 10;
    private static final int MIN_PRECISION = 1;

    public static List<Double> getTanExpansionCoefficients(int countOfCoefficients, int roundSignNumber) {
        
        if (countOfCoefficients < MIN_COEFFICIENT_COUNT || countOfCoefficients > MAX_COEFFICIENT_COUNT) {
            throw new IllegalArgumentException(
                String.format("Error: Count of expansion coefficients should be between %d and %d.",
                    MIN_COEFFICIENT_COUNT, MAX_COEFFICIENT_COUNT));
        }
        if (roundSignNumber < MIN_PRECISION || roundSignNumber > MAX_PRECISION) {
            throw new IllegalArgumentException(
                String.format("Error: Round Sign Number should be between %d and %d",
                    MIN_PRECISION, MAX_PRECISION));
        }

        List<Double> coefficients = new ArrayList<>();
        final int countOfTerms = (countOfCoefficients + 1) / 2;
        
        for (int n = 1; n <= countOfTerms; n++) {
            double coefficient = getTanSeriesCoefficient(n);
            coefficients.add(Precision.round(coefficient, roundSignNumber));
            coefficients.add(ZERO_COEFFICIENT);
        }
        if (coefficients.size() > countOfCoefficients) {
            coefficients.remove(countOfCoefficients);
        }
        
        return coefficients;
    }
    
    private static double getTanSeriesCoefficient(int n) {
        return bernoulliNumber(2 * n) * Math.pow(2, 2 * n) * 
            (Math.pow(2, 2 * n) - 1) / MathUtil.factorial(2 * n) * 1D * ((n + 1) % 2 == 0 ? 1 : -1);
    }
    
    private static double bernoulliNumber(int n) {
        double[] bernoulli = new double[n + 1];
        bernoulli[0] = 1;
        
        for (int m = 1; m <= n; m++) {
            bernoulli[m] = 0;
            for (int k = 0; k < m; k++) {
                bernoulli[m] -= binomialCoefficient(m + 1, k) * 1D * bernoulli[k] / (m + 1);
            }
        }
        
        return bernoulli[n];
    }
    
    private static long binomialCoefficient(int n, int k) {
        if (k == 0 || k == n) {
            return 1;
        }
        return MathUtil.factorial(n) / (MathUtil.factorial(k) * MathUtil.factorial(n - k));
    }
}