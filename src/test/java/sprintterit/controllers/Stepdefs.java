package sprintterit.controllers;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

public class Stepdefs {

    public static final boolean SLOWDOWN_TESTS_BY_SLEEPING = false;

    // HtmlUnitDriver is for Tracis-CI (which doesn't support ChromeDriver)
    // ChromeDriver is for local debugging (when you want to see what's going on)
    WebDriver driver = new HtmlUnitDriver();
    //WebDriver driver = new ChromeDriver();
    String baseUrl = "http://localhost:4567";

    @Given("^Add article is selected$")
    public void add_article_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Add article")).click();
    }

    @Given("^A new article with id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\" is created$")
    public void a_new_article_with_id_author_title_journal_and_year_is_created(String id, String author, String title, String journal, String year) throws Throwable {
        add_article_selected();
        addArticle(id, author, title, journal, year);
        article_is_added();
    }

    @When("^Id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void id_author_title_journal_and_year_are_given(String id, String author, String title, String journal, String year) throws Throwable {
        addArticle(id, author, title, journal, year);
    }

    @When("^Id \"([^\"]*)\", author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\", volume \"([^\"]*)\", number \"([^\"]*)\", year \"([^\"]*)\" and pages \"([^\"]*)\" to \"([^\"]*)\" are given$")
    public void id_author_title_journal_volume_number_year_and_pages_to_are_given(String id, String author, String title, String journal, String volume, String number, String year, String startpage, String endpage) throws Throwable {
        addArticle(id, author, title, journal, volume, number, year, startpage, endpage);
    }

    @Then("^Article is added$")
    public void article_is_added() throws Throwable {
        pageHasContent("Reference Management");
    }

    @Then("^Article is not added and error \"([^\"]*)\" is reported$")
    public void article_is_not_added_and_error_is_reported(String errorMessage) throws Throwable {
        pageHasContent(errorMessage);
        pageHasContent("New article");
    }

    @Then("^Article is not added$")
    public void article_is_not_added() throws Throwable {
        pageHasContent("<div id=\"error\"");
        pageHasContent("New article");
    }

    @Then("^Error \"([^\"]*)\" is reported$")
    public void error_is_reported(String errorMessage) throws Throwable {
        pageHasContent(errorMessage);
        pageHasContent("New article");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void addArticle(String id, String authors, String title, String journal, String year) {
        pageHasContent("New article");
        driver.findElement(By.name("id")).sendKeys(id);
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("journal")).sendKeys(journal);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("add")).submit();
        sleep();
    }

    private void addArticle(String id, String authors, String title, String journal, String volume, String number, String year, String startpage, String endpage) {
        pageHasContent("New article");
        driver.findElement(By.name("id")).sendKeys(id);
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("journal")).sendKeys(journal);
        driver.findElement(By.name("volume")).sendKeys(volume);
        driver.findElement(By.name("number")).sendKeys(number);
        driver.findElement(By.name("startpage")).sendKeys(startpage);
        driver.findElement(By.name("endpage")).sendKeys(endpage);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("add")).submit();
        sleep();
    }

    private void sleep() {
        if (SLOWDOWN_TESTS_BY_SLEEPING) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

}
