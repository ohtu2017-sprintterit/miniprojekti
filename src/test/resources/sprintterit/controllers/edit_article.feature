Feature: As a user I am able to edit an article reference

  Scenario: I can edit article with proper author, title, journal and year
    Given A new article with id "a", author "Matti", title "My article", journal "Journal" and year "1992" is created
    And Edit is selected
    When Article is changed with id "a", author "Teppo", title "My article 2", journal "Journal 2" and year "1993"
    Then Article is edited
