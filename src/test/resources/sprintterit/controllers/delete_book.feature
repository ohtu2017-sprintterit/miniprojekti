Feature: As a user I am able to delete a book reference

  Scenario: I can delete book reference
    Given A new book with author "Teppo", title "Book D", publisher "Publisher" and year "1984" is created
    And Reference page has content "Book D"
    When Delete reference "Teppo84" is selected
    Then Reference page does not have content "Book D"
