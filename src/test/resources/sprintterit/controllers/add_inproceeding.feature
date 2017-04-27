Feature: As a user I am able to add a proper inproceeding reference

  Scenario: I can add inproceeding with author, title, booktitle and year
      Given Add inproceeding is selected
       When Inproceeding with author "Teppo", title "My inproceeding", booktitle "Booktitle" and year "2000" are given
       Then Inproceeding is added
