Feature: As a user I am able to add an inproceeding reference

    Scenario: Add an inproceeding with id, author, title, booktitle and year
        Given Database is initialized
        When Inproceeding is added with id "I2", author "Matti", title "Title", booktitle "Booktitle", year 2005
        Then From database field "I2", author is set to "Matti", title to "Title", booktitle to "Booktitle", year 2005