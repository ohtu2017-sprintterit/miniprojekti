Feature: As a user I am able to edit an article reference

  Scenario: I can edit article with proper author, title, journal and year
    Given A new article with author "Teppo", title "Article 456", journal "Journal" and year "1993" is created
    And Reference page does not have content "Article 789"
    And Edit reference "Teppo93" is selected
    When Article title is changed to "Article 789"
    Then Reference page has content "Article 789"
