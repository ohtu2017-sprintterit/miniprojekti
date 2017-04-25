Feature: As a user I am able to add a book reference

    Scenario: Add a book with id, author, title, year and publisher
        Given Database is initialized
        When Book is added with id "B1", author "Matti", title "Title", year 2000, publisher "Publisher"
        Then From database field "B1", author is set to "Matti", title to "Title", year 2000, publisher to "Publisher"