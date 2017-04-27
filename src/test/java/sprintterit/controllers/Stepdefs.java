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
        driver.findElement(By.linkText("Article")).click();
    }

    @Given("^Add book is selected$")
    public void add_book_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Book")).click();
    }

    @Given("^A new article with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\" is created$")
    public void a_new_article_with_id_author_title_journal_and_year_is_created(String author, String title, String journal, String year) throws Throwable {
        add_article_selected();
        addArticle(author, title, journal, year);
        article_is_added();
    }

    @Given("^A new book with author \"([^\"]*)\", title \"([^\"]*)\", publisher \"([^\"]*)\" and year \"([^\"]*)\" is created$")
    public void a_new_book_with_id_author_title_publisher_and_year_is_created(String author, String title, String publisher, String year) throws Throwable {
        add_book_selected();
        addBook(author, title, publisher, year);
        book_is_added();
    }

    @Given("^Edit is selected$")
    public void edit_is_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Edit")).click();
    }

    @When("^Article with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void id_author_title_journal_and_year_are_given(String author, String title, String journal, String year) throws Throwable {
        addArticle(author, title, journal, year);
    }

    @When("^Book with author \"([^\"]*)\", title \"([^\"]*)\", publisher \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void book_id_author_title_publisher_and_year_are_given(String author, String title, String publisher, String year) throws Throwable {
        addBook(author, title, publisher, year);
    }

    @When("^Article with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\", volume \"([^\"]*)\", number \"([^\"]*)\", year \"([^\"]*)\" and pages \"([^\"]*)\" to \"([^\"]*)\" are given$")
    public void id_author_title_journal_volume_number_year_and_pages_to_are_given(String author, String title, String journal, String volume, String number, String year, String startpage, String endpage) throws Throwable {
        addArticle(author, title, journal, volume, number, year, startpage, endpage);
    }

    @When("^Article is changed with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\"$")
    public void article_is_changed_with_id_author_title_journal_and_year(String author, String title, String journal, String year) throws Throwable {
        editArticle(author, title, journal, year);
    }

    @When("^Book is changed with author \"([^\"]*)\", title \"([^\"]*)\", publisher \"([^\"]*)\" and year \"([^\"]*)\"$")
    public void book_is_changed_with_id_author_title_publisher_and_year(String author, String title, String publisher, String year) throws Throwable {
        //editBook(author, title, publisher, year);
    }

    @Then("^Article is added$")
    public void article_is_added() throws Throwable {
        pageHasContent("Reference Management");
    }

    @Then("^Article is edited$")
    public void article_is_edited() throws Throwable {
        pageHasContent("Reference Management");
    }

    @Then("^Book is added$")
    public void book_is_added() throws Throwable {
        pageHasContent("Reference Management");
    }

    @Then("^Book is edited$")
    public void book_is_edited() throws Throwable {
        //pageHasContent("Reference Management");
    }

    @Then("^Article is not added and error \"([^\"]*)\" is reported$")
    public void article_is_not_added_and_error_is_reported(String errorMessage) throws Throwable {
        pageHasContent(errorMessage);
        pageHasContent("New article");
    }

    @Then("^Book is not added and error \"([^\"]*)\" is reported$")
    public void book_is_not_added_and_error_is_reported(String errorMessage) throws Throwable {
        pageHasContent(errorMessage);
        pageHasContent("New book");
    }

    @Then("^Article is not added$")
    public void article_is_not_added() throws Throwable {
        pageHasContent("<div id=\"error\"");
        pageHasContent("New article");
    }

    @Then("^Book is not added$")
    public void book_is_not_added() throws Throwable {
        pageHasContent("<div id=\"error\"");
        pageHasContent("New book");
    }

    @Then("^Error \"([^\"]*)\" is reported$")
    public void error_is_reported(String errorMessage) throws Throwable {
        pageHasContent(errorMessage);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void addArticle(String authors, String title, String journal, String year) {
        pageHasContent("New article");
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("journal")).sendKeys(journal);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("add")).submit();
        sleep();
    }

    private void addArticle(String authors, String title, String journal, String volume, String number, String year, String startpage, String endpage) {
        pageHasContent("New article");
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

    private void editArticle(String authors, String title, String journal, String year) {
        pageHasContent("Edit article");
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("journal")).sendKeys(journal);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("save")).submit();
        sleep();
    }

    private void addBook(String authors, String title, String publisher, String year) {
        pageHasContent("New book");
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("publisher")).sendKeys(publisher);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("add")).submit();
        sleep();
    }

    private void editBook(String authors, String title, String publisher, String year) {
        pageHasContent("Edit book");
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("publisher")).sendKeys(publisher);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("save")).submit();
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
