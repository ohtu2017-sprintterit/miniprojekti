Feature: As a user I am able to add an article reference

    Scenario: Add an article with id, author, title, journal,  pages and year
        Given Database is initialized
        When Article is added with id "55", author "Matti", title "title", journal "Journal", startpage 10, endpage 20, year 1992
        Then From database field "55", author is set to "Matti", title to "title", journal to "Journal", startpage 10, endpage 20, year 1992