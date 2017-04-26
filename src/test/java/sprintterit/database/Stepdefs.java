package sprintterit.database;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import sprintterit.models.Article;
import sprintterit.models.Book;
import sprintterit.models.Inproceeding;
import sprintterit.models.Pages;

public class Stepdefs {

    private Database database;
    private ArticleDao articleDao;
    private BookDao bookDao;
    private InproceedingDao inproceedingDao;

    @Given("^Database is initialized$")
    public void article_init() throws Throwable {
        try {
            Files.delete(Paths.get("./testitietokanta2.db"));
        } catch (Exception e) {

        }
        database = new Database("jdbc:sqlite:testitietokanta2.db");
        database.init();
        articleDao = new ArticleDao(database);
        bookDao = new BookDao(database);
        inproceedingDao = new InproceedingDao(database);
    }

    @When("^Article is added with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\", startpage (\\d+), endpage (\\d+), year (\\d+)$")
    public void journal_is_set_to(String authors, String title, String journal, Integer startpage, Integer endpage, Integer year) throws Throwable {
        articleDao.addArticle("", authors, title, year, journal, 0, "", 0, new Pages(startpage, endpage), "", "", "", "");
    }

    @When("^Article is edited with id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\", startpage (\\d+), endpage (\\d+), year (\\d+)$")
    public void article_is_edited(String id, String authors, String title, String journal, Integer startpage, Integer endpage, Integer year) throws Throwable {
        articleDao.editArticle(id, authors, "", title, year, journal, 0, "", 0, new Pages(startpage, endpage), "", "", "", "");
    }

    @When("^Book is added with author \"([^\"]*)\", title \"([^\"]*)\", year (\\d+), publisher \"([^\"]*)\"$")
    public void book_is_added(String authors, String title, Integer year, String publisher) throws Throwable {
        bookDao.addBook("", authors, title, year, publisher, "", 0, "", "", "", "", "");
    }

    @When("^Book is edited with id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", year (\\d+), publisher \"([^\"]*)\"$")
    public void book_is_edited(String id, String authors, String title, Integer year, String publisher) throws Throwable {
        bookDao.editBook(id, authors, "", title, year, publisher, "", 0, "", "", "", "", "");
    }

    @When("^Inproceeding is added with author \"([^\"]*)\", title \"([^\"]*)\", booktitle \"([^\"]*)\", year (\\d+)$")
    public void inproceeding_is_added(String authors, String title, String booktitle, Integer year) throws Throwable {
        inproceedingDao.addInproceeding("", authors, title, year, booktitle, "", 0, "", "", null, "", "", "", "", "");
    }

    @When("^Inproceeding is edited with id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", booktitle \"([^\"]*)\", year (\\d+)$")
    public void inproceeding_is_edited(String id, String authors, String title, String booktitle, Integer year) throws Throwable {
        inproceedingDao.editInproceeding(id, authors, "", title, year, booktitle, "", 0, "", "", null, "", "", "", "", "");
    }

    @Then("^From database field \"([^\"]*)\", author is set to \"([^\"]*)\", title to \"([^\"]*)\", journal to \"([^\"]*)\", startpage (\\d+), endpage (\\d+), year (\\d+)$")
    public void journal_value(String id, String authors, String title, String journal, Integer startpage, Integer endpage, Integer year) throws Throwable {
        Article article = articleDao.findOne(id);
        assertEquals(article.getAuthors().toString(), authors);
        assertEquals(article.getYear(), year);
        assertEquals(article.getTitle(), title);
        assertEquals(article.getJournal(), journal);
    }

    @Then("^From database field \"([^\"]*)\", author is set to \"([^\"]*)\", title to \"([^\"]*)\", year (\\d+), publisher to \"([^\"]*)\"$")
    public void find_book(String id, String authors, String title, Integer year, String publisher) throws Throwable {
        Book book = bookDao.findOne(id);
        assertEquals(book.getAuthors().toString(), authors);
        assertEquals(book.getTitle(), title);
        assertEquals(book.getYear(), year);
        assertEquals(book.getPublisher(), publisher);
    }

    @Then("^From database field \"([^\"]*)\", author is set to \"([^\"]*)\", title to \"([^\"]*)\", booktitle to \"([^\"]*)\", year (\\d+)$")
    public void find_inproceeding(String id, String authors, String title, String booktitle, Integer year) throws Throwable {
        Inproceeding inproceeding = inproceedingDao.findOne(id);
        assertEquals(inproceeding.getAuthors().toString(), authors);
        assertEquals(inproceeding.getTitle(), title);
        assertEquals(inproceeding.getBooktitle(), booktitle);
        assertEquals(inproceeding.getYear(), year);
    }

}
