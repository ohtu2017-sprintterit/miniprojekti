package sprintterit;

import static spark.Spark.*;
import sprintterit.controllers.*;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.Database;
import sprintterit.database.InproceedingDao;
import sprintterit.database.ReferenceDao;

public class Main {

    private final ArticleDao articleDao;
    private final BookDao bookDao;
    private final InproceedingDao inproceedingDao;
    private final ReferenceDao referenceDao;

    /**
     * This service is available both online and locally
     *
     * Web: http://sprintterit-bibtex.herokuapp.com/
     * Local: http://localhost:4567/
     */
    public static void main(String[] args) {
        setStaticFileLocation();
        setPort(args);

        Database database = getDatabase(args);

        new Main(database).createControllers();
    }

    public Main(Database database) {
        this.articleDao = new ArticleDao(database);
        this.bookDao = new BookDao(database);
        this.inproceedingDao = new InproceedingDao(database);
        this.referenceDao = new ReferenceDao(database);
    }

    public void createControllers() {
        createIndex();
        createAdd();
        createEdit();
        createDelete();
        createBibtexGeneration();
        createACMImport();
    }

    private void createIndex() {
        get("/", new GetIndex(articleDao, bookDao, inproceedingDao));
    }

    private void createAdd() {
        get("/article", new GetView("article"));
        get("/book", new GetView("book"));
        get("/inproceeding", new GetView("inproceeding"));

        post("/article", new AddArticle(articleDao));
        post("/book", new AddBook(bookDao));
        post("/inproceeding", new AddInproceeding(inproceedingDao));
    }

    private void createEdit() {
        get("/article/:id", new ViewArticle(articleDao));
        get("/book/:id", new ViewBook(bookDao));
        get("/inproceeding/:id", new ViewInproceeding(inproceedingDao));

        post("/edit_article", new EditArticle(articleDao));
        post("/edit_book", new EditBook(bookDao));
        post("/edit_inproceeding", new EditInproceeding(inproceedingDao));
    }

    private void createDelete() {
        post("/delete_article/:id", new Delete(articleDao));
        post("/delete_book/:id", new Delete(bookDao));
        post("/delete_inproceeding/:id", new Delete(inproceedingDao));
    }

    private void createBibtexGeneration() {
        post("/generatebibtex", new GenerateBibtexFile(referenceDao));
    }

    private void createACMImport() {
        ACMImportController acmController = new ACMImportController(articleDao, bookDao, inproceedingDao);
        get("/acmimport", acmController);
        post("/acmimport", acmController);
    }

    private static void setStaticFileLocation() {
        // This directory includes the CSS files for Bootstrap
        // These are static files which the web server will serve as is
        staticFileLocation("/web");
    }

    private static void setPort(String[] args) throws NumberFormatException {
        // Cucumber tests use a command line argument to specify the port
        if (args != null && args.length >= 1) {
            port(Integer.valueOf(args[0]));
        }

        // Heroku specifies the port using an environment variable
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
    }

    private static Database getDatabase(String[] args) {
        String address = getDatabaseAddress(args);
        Database database = new Database(address);
        database.init();

        return database;
    }

    private static String getDatabaseAddress(String[] args) {
        // If run locally, we use a lightweight SQLite database
        String jdbcAddress = "jdbc:sqlite:tietokantatiedosto.db";

        // Functional tests (Cucumber) use a different SQLite database file
        // The file is specified as a command line argument
        if (args != null && args.length >= 2) {
            jdbcAddress = args[1];
        }

        // On Heroku, we use a PostgreSQL database
        // Heroku specifies the address using an environment variable
        if (System.getenv("JDBC_DATABASE_URL") != null) {
            jdbcAddress = System.getenv("JDBC_DATABASE_URL");
        }

        return jdbcAddress;
    }

}
