package com.mycompany.app.steps;

import com.mycompany.app.powerseries.PowerSeries;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerSeriesSteps {

    private int countOfCoefficients;
    private int roundSignNumber;
    private List<Double> coefficients;
    private IllegalArgumentException exception;

    @Given("I want to calculate coefficients for tangent series expansion")
    public void i_want_to_calculate_coefficients_for_tangent_series_expansion() {
    }

    @When("I request {int} coefficients with {int} decimal places rounding")
    public void i_request_coefficients_with_decimal_places_rounding(Integer count, Integer precision) {
        this.countOfCoefficients = count;
        this.roundSignNumber = precision;
        try {
            this.coefficients = PowerSeries.getTanExpansionCoefficients(count, precision);
        } catch (IllegalArgumentException e) {
            this.exception = e;
        }
    }

    @Then("I should receive the following coefficients: {string}")
    public void i_should_receive_the_following_coefficients(String expectedCoefficients) {
        List<Double> expected = parseCoefficients(expectedCoefficients);
        assertEquals(expected, coefficients, "Coefficients do not match expected values");
    }

    @Then("I should receive an error message: {string}")
    public void i_should_receive_an_error_message(String expectedErrorMessage) {
        assertEquals(expectedErrorMessage, exception.getMessage(), "Error message does not match");
    }

    private List<Double> parseCoefficients(String coefficientsString) {
        String[] parts = coefficientsString
            .replace("[", "")
            .replace("]", "")
            .split(", ");
        return List.of(parts).stream()
            .map(Double::parseDouble)
            .toList();
    }
}