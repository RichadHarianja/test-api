Feature: Compare response between two API

  Scenario Outline: Hit API process
    Given I have hit first API with "<first>" endpoint
    Then I hit the second API with "<second>" endpoint
    And I want to compare between "<both>" of the API
    Examples:
      | first | second  | both  |
      | src/test/resources/data/file.txt | src/test/resources/data/file1.txt |  src/test/resources/response/ |
