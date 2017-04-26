Feature: As a user I am able to add an inproceeding reference

    Scenario: Add an inproceeding with author, title, booktitle and year
        Given Database is initialized
        When Inproceeding is added with author "Matti", title "Title", booktitle "Booktitle", year 2005
        Then From database field "Matti05", author is set to "Matti", title to "Title", booktitle to "Booktitle", year 2005