Feature: As a user I am able to add an article reference

    Scenario: Add an article with author, title, journal, pages and year
        Given Database is initialized
        When Article is added with author "Matti", title "title", journal "Journal", startpage 10, endpage 20, year 1992
        Then From database field "Matti92", author is set to "Matti", title to "title", journal to "Journal", startpage 10, endpage 20, year 1992