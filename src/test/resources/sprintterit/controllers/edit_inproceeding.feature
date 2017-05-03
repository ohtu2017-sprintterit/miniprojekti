Feature: As a user I am able to edit an inproceeding reference

  Scenario: I can edit inproceeding with proper author, title, journal and year
    Given A new inproceeding with author "Teppo", title "Inproceeding 456", booktitle "Booktitle" and year "1995" is created
    And Reference page does not have content "Inproceeding 789"
    And Edit reference "Teppo95" is selected
    When Inproceeding title is changed to "Inproceeding 789"
    Then Reference page has content "Inproceeding 789"
