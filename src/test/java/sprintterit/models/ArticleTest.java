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
        article = new Article("1", authors, "Cognitive apprenticeship: making thinking visible",
                "American Educator", 6, 0, "Feb", 38, 46, 1991, "publisher", "address", "note", "key");
    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals(authors.toString(), article.getAuthors().toString());
        assertEquals("1", article.getId());
        assertEquals("Cognitive apprenticeship: making thinking visible", article.getTitle());
        assertEquals("American Educator", article.getJournal());
        assertEquals(Integer.valueOf(6), article.getVolume());
        assertEquals("Feb", article.getMonth());
        assertEquals(Integer.valueOf(38), article.getStartpage());
        assertEquals(Integer.valueOf(46), article.getEndpage());
        assertEquals(1991, article.getYear());
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
        article.setStartpage(94);
        article.setEndpage(99);
        assertEquals(Integer.valueOf(94), article.getStartpage());
        assertEquals(Integer.valueOf(99), article.getEndpage());
        assertEquals("94--99", article.getPages());
    }

    @Test
    public void setPagesWorksCorrectly2() {
        article.setPages(94, 99);
        assertEquals(Integer.valueOf(94), article.getStartpage());
        assertEquals(Integer.valueOf(99), article.getEndpage());
        assertEquals("94--99", article.getPages());
    }

    @Test
    public void setYearWorksCorrectly() {
        article.setYear(2003);
        assertEquals(2003, article.getYear());
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
                new Authors("Author, Adam\nWriter, William"),
                "A whole lot of nothing",
                "Trash",
                3,
                5,
                null,
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
