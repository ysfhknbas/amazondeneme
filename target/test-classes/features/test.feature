@amazon
  Feature: amazon seller product price check and import it to excel file

    Scenario: excel automation
      Given navigate amazon web site sellers product
      And check if page exist
      And if page exist get price and write on excel if not end test
      Then close driver