Feature: As a user I am able to edit a book reference

  Scenario: I can edit book with proper authors, title, publisher and year
    Given A new book with author "Teppo", title "Book 456", publisher "Publisher" and year "1994" is created
    And Reference page does not have content "Book 789"
    And Edit reference "Teppo94" is selected
    When Book title is changed to "Book 789"
    Then Reference page has content "Book 789"
