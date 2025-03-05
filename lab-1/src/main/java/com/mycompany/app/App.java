package com.mycompany.app;

import java.util.List;
import java.util.Locale;

import com.mycompany.app.avltree.AVLTree;
import com.mycompany.app.powerseries.PowerSeries;
import com.mycompany.app.util.MathUtil;

public class App {
    public static void main(String[] args) {
        final int countOfTerms = 11;
        final int roundSingNumber = 5;
        List<Double> coefficients = PowerSeries.getTanExpansionCoefficients(countOfTerms, roundSingNumber);
        coefficients.forEach(x -> System.out.print(String.format(Locale.US, "%." + roundSingNumber + "f ", x)));


        AVLTree avlTree = new AVLTree();
        
    }
}
