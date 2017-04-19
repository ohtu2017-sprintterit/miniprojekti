package sprintterit.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    Authors authors;
    Book book;

    @Before
    public void setUp() {
        authors = new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann");
        book = new Book("123", authors, "C programming", 1984, "Publisher", "Scienceroad",
                6, "Programming", "edition", "Feb", "note", "key");

    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals("123", book.getId());
        assertEquals(authors.toString(), book.getAuthors().toString());
        assertEquals("C programming", book.getTitle());
        assertEquals(1984, book.getYear());
        assertEquals("Publisher", book.getPublisher());
        assertEquals("Scienceroad", book.getAddress());
        assertEquals(6, book.getVolume());
        assertEquals("Programming", book.getSeries());
        assertEquals("edition", book.getEdition());
        assertEquals("Feb", book.getMonth());
        assertEquals("note", book.getNote());
        assertEquals("key", book.getKey());
    }

    @Test
    public void toStringWorksCorrectly() {
        String expected = String.join(
                "\n",
                "@book{123,",
                "  author = {Collins, Allan and Brown, John Seely and Holum, Ann},",
                "  title = {C programming},",
                "  publisher = {Publisher},",
                "  year = \"1984\",",
                "  volume = \"6\",",
                "  series = \"Programming\",",
                "  address = \"Scienceroad\",",
                "  edition = \"edition\",",
                "  month = Feb,",
                "  note = {note},",
                "  key = {key}",
                "}\n"
        );

        assertEquals(expected, book.toString());
    }
}
