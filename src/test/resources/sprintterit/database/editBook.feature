Feature: As a user I am able to edit a book reference

    Scenario: Edit a book with id, author, title, year and publisher
        Given Database is initialized
        And Book is added with author "Maija", title "Title", year 2000, publisher "Publisher"
        When Book is edited with id "Maija00", author "Teppo", title "title", year 2001, publisher "publisher"
        Then From database field "Maija00", author is set to "Teppo", title to "title", year 2001, publisher to "publisher"