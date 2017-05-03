Feature: As a user I am able to delete an inproceeding reference

  Scenario: I can delete inproceeding reference
    Given A new inproceeding with author "Teppo", title "Inproceeding D", booktitle "Booktitle" and year "1985" is created
    And Reference page has content "Inproceeding D"
    When Delete reference "Teppo85" is selected
    Then Reference page does not have content "Inproceeding D"
