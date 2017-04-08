package sprintterit.database;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import static org.junit.Assert.*;
import sprintterit.models.Article;

public class Stepdefs {

    private Database database;
    private ArticleDao articleDao;
    private Connection connection;

    @Given("^Database is initialized$")
    public void article_init() throws Throwable {
        try {
            Files.delete(Paths.get("./testitietokanta.db"));
        } catch (Exception e) {
            
        }
        database = new Database("jdbc:sqlite:testitietokanta.db");
        database.init();
        articleDao = new ArticleDao(database);
    }

    @When("^Article is added with id \"([^\"]*)\", author \"([^\"]*)\", startpage (\\d+), endpage (\\d+), year (\\d+)$")
    public void journal_is_set_to(String id, String authors, int startpage, int endpage, int year) throws Throwable {
        articleDao.addArticle(id, authors, "", year, "", 0, "", 0, startpage, endpage, "", "", "", "");
    }

    @Then("^From database field \"([^\"]*)\", author is set to \"([^\"]*)\", startpage (\\d+), endpage (\\d+), year (\\d+)$")
    public void journal_value(String id, String authors, int startpage, int endpage, int year) throws Throwable {
        Article article = articleDao.findOne(id);
        assertEquals(article.getAuthors().toString(), authors);
        assertEquals(article.getYear(), year);
    }
}
