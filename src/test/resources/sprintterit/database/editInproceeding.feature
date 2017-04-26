Feature: As a user I am able to edit an inproceeding reference

    Scenario: Edit an inproceeding with id, author, title, booktitle and year
        Given Database is initialized
        And Inproceeding is added with author "Anneli", title "Title", booktitle "Booktitle", year 2005
        When Inproceeding is edited with id "Anneli05", author "Teppo", title "title", booktitle "booktitle", year 2006
        Then From database field "Anneli05", author is set to "Teppo", title to "title", booktitle to "booktitle", year 2006