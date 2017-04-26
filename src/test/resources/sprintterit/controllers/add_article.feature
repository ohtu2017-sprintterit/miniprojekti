Feature: As a user I am able to add a proper article reference

  Scenario: I can add article with author, title, journal and year
      Given Add article is selected
       When Article with author "Matti", title "My article", journal "Journal" and year "1992" are given
       Then Article is added

  Scenario: I cannot add article if any of the required fields is missing
      Given Add article is selected
       When Article with author "", title "", journal "" and year "" are given
       Then Article is not added
        And Error "Authors is required" is reported
        And Error "Title is required" is reported
        And Error "Journal is required" is reported
        And Error "Year is required" is reported

  Scenario: I cannot add article with non-integer volume, number, year or pages
      Given Add article is selected
       When Article with author "Matti", title "Non-integer", journal "Journal", volume "vol 1", number "no 1", year "c. 1992" and pages "pp. 100" to "pp. 109" are given
       Then Article is not added
        And Error "Volume should be an integer" is reported
        And Error "Number should be an integer" is reported
        And Error "Year should be an integer" is reported
        And Error "Startpage should be an integer" is reported
        And Error "Endpage should be an integer" is reported

  Scenario: I cannot add article with unspecified author
      Given Add article is selected
       When Article with author "", title "My article", journal "Journal" and year "1992" are given
       Then Article is not added and error "Authors is required" is reported

  Scenario: I cannot add article with unspecified title
      Given Add article is selected
       When Article with author "Matti", title "", journal "Journal" and year "1992" are given
       Then Article is not added and error "Title is required" is reported

  Scenario: I cannot add article with unspecified journal
      Given Add article is selected
       When Article with author "Matti", title "My article", journal "" and year "1992" are given
       Then Article is not added and error "Journal is required" is reported

  Scenario: I cannot add article with unspecified year
      Given Add article is selected
       When Article with author "Matti", title "My article", journal "Journal" and year "" are given
       Then Article is not added and error "Year is required" is reported
