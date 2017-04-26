Feature: As a user I am able to edit a book reference

  Scenario: I can edit book with proper authors, title, publisher and year
    Given A new book with author "Teppo", title "Book 123", publisher "Publisher" and year "1999" is created
    And Edit is selected
    When Book is changed with author "Seppo", title "Book 12345", publisher "Publisher 2" and year "2000"
    Then Book is edited
