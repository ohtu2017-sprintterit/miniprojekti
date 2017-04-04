package sprintterit.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArticleTest {

    Authors authors;
    Pages pages;
    Article article;

    @Before
    public void setUp() {
        authors = new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann");
        pages = new Pages(38, 46);
        article = new Article("1", authors, "Cognitive apprenticeship: making thinking visible",
                "American Educator", 6, 0, pages, 1991);
    }

    @Test
    public void constructorWorksCorrectly() {
        assertEquals(authors.toString(), article.getAuthors().toString());
        assertEquals("1", article.getId());
        assertEquals("Cognitive apprenticeship: making thinking visible", article.getTitle());
        assertEquals("American Educator", article.getJournal());
        assertEquals(6, article.getVolume());
        assertEquals(pages, article.getPages());
        assertEquals(1991, article.getYear());
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
        assertEquals(35, article.getVolume());
    }

    @Test
    public void setNumberWorksCorrectly() {
        article.setNumber(4);
        assertEquals(4, article.getNumber());
    }

    @Test
    public void setPagesWorksCorrectly() {
        Pages p = new Pages(94, 99);
        article.setPages(p);
        assertEquals(p, article.getPages());
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

}
