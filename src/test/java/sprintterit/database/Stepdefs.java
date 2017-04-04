package sprintterit.database;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

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
        connection = database.getConnection();
    }

    @When("^Article is added with id \"([^\"]*)\" author \"([^\"]*)\" year (\\d+)$")
    public void journal_is_set_to(String id, String authors, int year) throws Throwable {
        articleDao.addArticle(id, authors, "", year, "", 0, 0, "", "", "");
    }

    @Then("^From database field \"([^\"]*)\" author is set to \"([^\"]*)\" and year (\\d+)$")
    public void journal_value(String id, String authors, int year) throws Throwable {
        PreparedStatement statement = connection.prepareStatement(
                "SELECT authors, year FROM Reference WHERE id = ?");
        statement.setString(1, id);
        ResultSet s = statement.executeQuery();
        assertEquals(s.getString(1), authors);
        assertEquals(s.getInt(2), year);
    }
}
