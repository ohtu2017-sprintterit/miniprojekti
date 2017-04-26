Feature: As a user I am able to add a proper book reference

    Scenario: I can add book with authors, title, publisher and year
        Given Add book is selected
        When Book with author "Teppo", title "Book 123", publisher "Publisher" and year "1999" are given
        Then Book is added

    Scenario: I cannot add book if any of the required fields is missing
        Given Add book is selected
        When Book with author "", title "", publisher "" and year "" are given
        Then Book is not added
        And Error "Authors is required" is reported
        And Error "Title is required" is reported
        And Error "Publisher is required" is reported
        And Error "Year is required" is reported