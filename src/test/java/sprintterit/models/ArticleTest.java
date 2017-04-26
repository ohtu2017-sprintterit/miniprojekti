package sprintterit.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {

    Authors authors;
    Article article;

    @Before
    public void setUp() {
        authors = new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann");
        article = new Article("1", "tags", authors, "Cognitive apprenticeship: making thinking visible",
                "American Educator", 6, 0, "Feb", new Pages(38, 46), 1991,
                "publisher", "address", "note", "key");
    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals(authors.toString(), article.getAuthors().toString());
        assertEquals("1", article.getId());
        assertEquals("Cognitive apprenticeship: making thinking visible", article.getTitle());
        assertEquals("American Educator", article.getJournal());
        assertEquals(Integer.valueOf(6), article.getVolume());
        assertEquals("Feb", article.getMonth());
        assertEquals(38, article.getPages().getBegin());
        assertEquals(46, article.getPages().getEnd());
        assertEquals(Integer.valueOf(1991), article.getYear());
        assertEquals("publisher", article.getPublisher());
        assertEquals("address", article.getAddress());
        assertEquals("note", article.getNote());
        assertEquals("key", article.getKey());
    }

    @Test
    public void setAuthorsWorkCorrectly() {
        Authors a = new Authors("Bruhn, Russel E.\nBurton, Philip J.");
        article.setAuthors(a);
        assertEquals(a, article.getAuthors());
    }

    @Test
    public void setTitleWorksCorrectly() {
        String title = "An approach to teaching Java using computers";
        article.setTitle(title);
        assertEquals(title, article.getTitle());
    }

    @Test
    public void setJournalWorksCorrectly() {
        String journal = "SIGCSE Bull.";
        article.setJournal(journal);
        assertEquals(journal, article.getJournal());
    }

    @Test
    public void setVolumeWorksCorrectly() {
        article.setVolume(35);
        assertEquals(Integer.valueOf(35), article.getVolume());
    }

    @Test
    public void setNumberWorksCorrectly() {
        article.setNumber(4);
        assertEquals(Integer.valueOf(4), article.getNumber());
    }

    @Test
    public void setPagesWorksCorrectly() {
        Pages p = new Pages(94, 99);
        article.setPages(p);
        assertEquals(p, article.getPages());
        assertEquals(94, article.getPages().getBegin());
        assertEquals(99, article.getPages().getEnd());
    }

    @Test
    public void setYearWorksCorrectly() {
        article.setYear(2003);
        assertEquals(Integer.valueOf(2003), article.getYear());
    }

    @Test
    public void setPublisherWorksCorrectly() {
        article.setPublisher("ACM");
        assertEquals("ACM", article.getPublisher());
    }

    @Test
    public void setAddressWorksCorrectly() {
        article.setAddress("USA");
        assertEquals("USA", article.getAddress());
    }

    @Test
    public void toStringWorksCorrectly() {
        Article a = new Article(
                "a",
                "tags",
                new Authors("Author, Adam\nWriter, William"),
                "A whole lot of nothing",
                "Trash",
                3,
                5,
                null,
                null,
                2007,
                null,
                null,
                null,
                null
        );

        String expected = String.join(
                "\n",
                "@article{a,",
                "  author = {Author, Adam and Writer, William},",
                "  title = {A whole lot of nothing},",
                "  journal = {Trash},",
                "  year = \"2007\",",
                "  volume = \"3\",",
                "  number = \"5\"",
                "}\n"
        );

        assertEquals(expected, a.toString());
    }
}
