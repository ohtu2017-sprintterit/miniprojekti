Feature: As a user I am able to edit an article reference

    Scenario: Edit an article with id, author, title, journal, pages and year
        Given Database is initialized
        And Article is added with author "Matti", title "title", journal "Journal", startpage 10, endpage 20, year 1992
        When Article is edited with id "Matti92", author "Teppo", title "title", journal "journal", startpage 11, endpage 21, year 1993
        Then From database field "Matti92", author is set to "Teppo", title to "title", journal to "journal", startpage 11, endpage 21, year 1993