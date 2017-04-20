Feature: As a user I am able to add a proper book reference

    Scenario: I can add book with proper id, authors, title, publisher and year
        Given Add book is selected
        When Book with id "teppo", author "Teppo", title "Book 123", publisher "Publisher" and year "1999" are given
        Then Book is added

    Scenario: I cannot add book with duplicate id
        Given A new book with id "book1", author "Teppo", title "Book 123", publisher "Publisher" and year "1999" is created
        And Add book is selected
        When Book with id "book1", author "Teppo", title "Book 123", publisher "Publisher" and year "1999" are given
        Then Book is not added and error "BibTeX key is not unique (already taken by another reference)" is reported

    Scenario: I cannot add book if any of the required fields is missing
        Given Add book is selected
        When Book with id "", author "", title "", publisher "" and year "" are given
        Then Book is not added
        And Error "BibTeX key is required" is reported
        And Error "Authors is required" is reported
        And Error "Title is required" is reported
        And Error "Publisher is required" is reported
        And Error "Year is required" is reported