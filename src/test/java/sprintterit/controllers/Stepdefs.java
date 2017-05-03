package sprintterit.controllers;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
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

    @Given("^Add book is selected$")
    public void add_book_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Add book")).click();
    }

    @Given("^Add inproceeding is selected$")
    public void add_inproceeding_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Add inproceeding")).click();
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

    @Given("^A new inproceeding with author \"([^\"]*)\", title \"([^\"]*)\", booktitle \"([^\"]*)\" and year \"([^\"]*)\" is created$")
    public void a_new_inproceeding_with_id_author_title_booktitle_and_year_is_created(String author, String title, String booktitle, String year) throws Throwable {
        add_inproceeding_selected();
        addInproceeding(author, title, booktitle, year);
        inproceeding_is_added();
    }

    @Given("^Edit reference \"([^\"]*)\" is selected$")
    public void edit_reference_is_selected(String id) throws Throwable {
        driver.findElement(By.xpath("//a[contains(@href, '" + id + "')]")).click();
    }

    @Given("^Reference page does not have content \"([^\"]*)\"$")
    public void reference_page_does_not_have_content(String content) throws Throwable {
        pageHasContent("Search");
        pageDoesNotHaveContent(content);
    }

    @Given("^Import from ACM DL is selected$")
    public void acm_import_is_selected() throws Throwable {
        driver.get(baseUrl);
        sleep();
        driver.findElement(By.linkText("Import from ACM DL")).click();
    }

    @When("^Article with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void id_author_title_journal_and_year_are_given(String author, String title, String journal, String year) throws Throwable {
        addArticle(author, title, journal, year);
    }

    @When("^Book with author \"([^\"]*)\", title \"([^\"]*)\", publisher \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void book_id_author_title_publisher_and_year_are_given(String author, String title, String publisher, String year) throws Throwable {
        addBook(author, title, publisher, year);
    }

    @When("^Inproceeding with author \"([^\"]*)\", title \"([^\"]*)\", booktitle \"([^\"]*)\" and year \"([^\"]*)\" are given$")
    public void inproceeding_with_author_title_booktitle_and_year_are_given(String author, String title, String booktitle, String year) throws Throwable {
        addInproceeding(author, title, booktitle, year);
    }

    @When("^Article with author \"([^\"]*)\", title \"([^\"]*)\", journal \"([^\"]*)\", volume \"([^\"]*)\", number \"([^\"]*)\", year \"([^\"]*)\" and pages \"([^\"]*)\" to \"([^\"]*)\" are given$")
    public void id_author_title_journal_volume_number_year_and_pages_to_are_given(String author, String title, String journal, String volume, String number, String year, String startpage, String endpage) throws Throwable {
        addArticle(author, title, journal, volume, number, year, startpage, endpage);
    }

    @When("^Book title is changed to \"([^\"]*)\"$")
    public void book_edit(String title) throws Throwable {
        editBook(title);
    }

    @When("^Article title is changed to \"([^\"]*)\"$")
    public void article_edit(String title) throws Throwable {
        editArticle(title);
    }

    @When("^Inproceeding title is changed to \"([^\"]*)\"$")
    public void inproceeding_edit(String title) throws Throwable {
        editInproceeding(title);
    }

    @When("^Delete reference \"([^\"]*)\" is selected$")
    public void delete_reference_is_selected(String id) throws Throwable {
        driver.findElement(By.xpath("//form[contains(@action, '" + id + "')]")).submit();
    }

    @When("^Import URL \"([^\"]*)\" is entered$")
    public void enter_import_url(String url) throws Throwable {
        driver.findElement(By.id("acmurl")).sendKeys(url);
    }

    @When("^Import is clicked$")
    public void import_is_clicked() throws Throwable {
        driver.findElement(By.cssSelector("input[value=\"Import\"]")).click();
    }

    @Then("^Reference \"([^\"]*)\" is imported$")
    public void reference_is_imported(String title) {
        pageHasContent("Successfully imported reference");
        pageHasContent(title);
    }

    @Then("^Import fails$")
    public void import_fails() throws Throwable {
        pageHasContent("Error");
    }

    @Then("^Article is added$")
    public void article_is_added() throws Throwable {
        pageHasContent("Reference management");
    }

    @Then("^Book is added$")
    public void book_is_added() throws Throwable {
        pageHasContent("Reference management");
    }

    @Then("^Inproceeding is added$")
    public void inproceeding_is_added() throws Throwable {
        pageHasContent("Reference management");
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

    @Then("^Reference page has content \"([^\"]*)\"$")
    public void reference_page_has_content(String content) throws Throwable {
        pageHasContent("Search");
        pageHasContent(content);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    /* helper methods */
    private void pageHasContent(String content) {
        assertTrue(driver.getPageSource().contains(content));
    }

    private void pageDoesNotHaveContent(String content) {
        assertTrue(!driver.getPageSource().contains(content));
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

    private void addInproceeding(String authors, String title, String booktitle, String year) {
        pageHasContent("New inproceeding");
        driver.findElement(By.name("authors")).sendKeys(authors);
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("booktitle")).sendKeys(booktitle);
        driver.findElement(By.name("year")).sendKeys(year);
        sleep();
        driver.findElement(By.name("add")).submit();
        sleep();
    }

    private void editArticle(String title) {
        pageHasContent("Edit article");
        driver.findElement(By.name("title")).sendKeys(title);
        sleep();
        driver.findElement(By.name("save")).submit();
        sleep();
    }

    private void editBook(String title) {
        pageHasContent("Edit book");
        driver.findElement(By.name("title")).sendKeys(title);
        sleep();
        driver.findElement(By.name("save")).submit();
        sleep();
    }

    private void editInproceeding(String title) {
        pageHasContent("Edit inproceeding");
        driver.findElement(By.name("title")).sendKeys(title);
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
