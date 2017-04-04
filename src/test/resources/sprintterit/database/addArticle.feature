Feature: As a user I am able to add an article reference

    Scenario: Add an article with id, author, pages and year
        Given Database is initialized
        When Article is added with id "55", author "Matti", startpage 10, endpage 20, year 1992
        Then From database field "55", author is set to "Matti", startpage 10, endpage 20, year 1992