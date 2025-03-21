Feature: Power Series Expansion for Tangent Function

  Scenario: Successfully get coefficients for tangent series expansion
    Given I want to calculate coefficients for tangent series expansion
    When I request 5 coefficients with 4 decimal places rounding
    Then I should receive the following coefficients: "[1.0, 0.0, 0.3333, 0.0, 0.1333]"

  Scenario: Fail to get coefficients due to invalid count of coefficients
    Given I want to calculate coefficients for tangent series expansion
    When I request 0 coefficients with 4 decimal places rounding
    Then I should receive an error message: "Error: Count of expansion coefficients should be between 1 and 18."

  Scenario: Fail to get coefficients due to invalid rounding precision
    Given I want to calculate coefficients for tangent series expansion
    When I request 5 coefficients with 0 decimal places rounding
    Then I should receive an error message: "Error: Round Sign Number should be between 1 and 10"