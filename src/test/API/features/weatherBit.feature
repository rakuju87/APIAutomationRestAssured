@APItesting
Feature: Weather forecast

  Scenario Outline: As a choosey surfer want to check for weather forecast for 16 days to surf
    Given I like to surf in <Beach> of Sydney with <PostalCode>
    Then I check temp on <day1> and <day2> is between <minTemp> to <maxTemp>
    And I check to see if UV index is less than or equal <UV>

    Examples:
      | Beach      |PostalCode |day1     |day2   |minTemp|maxTemp| UV |
      | Bondi      | 2026      |Thursday |Friday |15     |35     | 20 |
      | Cronulla   | 2230      |Thursday |Friday |15     |35     | 20 |
      | Avoca      | 2251      |Thursday |Friday |15     |35     | 20 |
      | Manly      | 2095      |Thursday |Friday |15     |35     | 20 |
      | Bulli      | 2516      |Thursday |Friday |15     |35     | 20 |
      | Wollongong | 2500      |Thursday |Friday |15     |35     | 20 |
      | Perkins    | 2502      |Thursday |Friday |15     |35     | 20 |
      | Kiama      | 2533      |Thursday |Friday |15     |35     | 20 |
      | Hyams      | 2540      |Thursday |Friday |15     |35     | 20 |
      | Narrawallee| 2539      |Thursday |Friday |15     |35     | 20 |
