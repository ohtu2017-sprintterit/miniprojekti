Feature: As a user I am able to import references from the ACM Digital Library

  Scenario: Importing a reference from the ACM DL
    Given Import from ACM DL is selected
    When Import URL "http://dl.acm.org/citation.cfm?id=2380552.2380613" is entered
    And Import is clicked
    Then Reference "Three Years of Design-based Research to Reform a Software Engineering Curriculum" is imported