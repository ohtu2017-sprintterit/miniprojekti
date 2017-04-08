package sprintterit.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InproceedingTest {

    Authors authors;
    Pages pages;
    Inproceeding inproceeding;

    @Before
    public void setUp() {
        authors = new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann");
        pages = new Pages(38, 46);
        inproceeding = new Inproceeding("1234", authors, "title", "booktitle", pages, 1999, "editor",
                "series", "month", "org", "publisher", "address", "note", "key");

    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals("1234", inproceeding.getId());
        assertEquals(authors.toString(), inproceeding.getAuthors().toString());
        assertEquals("title", inproceeding.getTitle());
        assertEquals("booktitle", inproceeding.getBooktitle());
        assertEquals(pages.toString(), inproceeding.getPages().toString());
        assertEquals(1999, inproceeding.getYear());
        assertEquals("editor", inproceeding.getEditor());
        assertEquals("series", inproceeding.getSeries());
        assertEquals("month", inproceeding.getMonth());
        assertEquals("org", inproceeding.getOrganization());
        assertEquals("publisher", inproceeding.getPublisher());
        assertEquals("address", inproceeding.getAddress());
        assertEquals("note", inproceeding.getNote());
        assertEquals("key", inproceeding.getKey());
    }

}
