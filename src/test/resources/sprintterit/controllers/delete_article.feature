Feature: As a user I am able to delete an article reference

  Scenario: I can delete article reference
    Given A new article with author "Teppo", title "Article D", journal "Journal" and year "1983" is created
    And Reference page has content "Article D"
    When Delete reference "Teppo83" is selected
    Then Reference page does not have content "Article D"
