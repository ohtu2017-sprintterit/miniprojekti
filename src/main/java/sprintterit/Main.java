package sprintterit;

import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.bibtexgen.BibtexGenerator;
import sprintterit.controllers.*;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.Database;
import sprintterit.database.InproceedingDao;
import sprintterit.database.ReferenceDao;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Osoite: http://sprintterit-bibtex.herokuapp.com/
        // Paikallinen versio: http://localhost:4567/

        // Bootstrapin tyylitiedoston sijainti
        // Kansion web sisältö näkyy verkkoon sellaisenaan
        staticFileLocation("/web");

        // Asetetaan portti, jos heroku antaa PORT-ympäristömuuttujan
        // Vastaavasti Cucumber-testit käyttävät komentoriviparametria
        if (args != null && args.length >= 1) {
            port(Integer.valueOf(args[0]));
        }
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // Käytetään oletuksena paikallista sqlite-tietokantaa
        // Jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        // Vastaavasti Cucumber-testit käyttävät komentoriviparametria
        String jdbcOsoite = "jdbc:sqlite:tietokantatiedosto.db";
        if (args != null && args.length >= 2) {
            jdbcOsoite = args[1];
        }
        if (System.getenv("JDBC_DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("JDBC_DATABASE_URL");
        }
        Database database = new Database(jdbcOsoite);
        database.init();

        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        InproceedingDao inproceedingDao = new InproceedingDao(database);
        ReferenceDao referenceDao = new ReferenceDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            if (req.queryParams("search") != null) {
                if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("tag")) {
                    map.put("articles", articleDao.findTag(req.queryParams("search")));
                    map.put("books", bookDao.findTag(req.queryParams("search")));
                    map.put("inproceedings", inproceedingDao.findTag(req.queryParams("search")));
                } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("author")) {
                    map.put("articles", articleDao.findAuthor(req.queryParams("search")));
                    map.put("books", bookDao.findAuthor(req.queryParams("search")));
                    map.put("inproceedings", inproceedingDao.findAuthor(req.queryParams("search")));
                } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("title")) {
                    map.put("articles", articleDao.findTitle(req.queryParams("search")));
                    map.put("books", bookDao.findTitle(req.queryParams("search")));
                    map.put("inproceedings", inproceedingDao.findTitle(req.queryParams("search")));
                } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("year")
                        && req.queryParams("search").matches("^[0-9]+$")) {
                    map.put("articles", articleDao.findYear(Integer.parseInt(req.queryParams("search"))));
                    map.put("books", bookDao.findYear(Integer.parseInt(req.queryParams("search"))));
                    map.put("inproceedings", inproceedingDao.findYear(Integer.parseInt(req.queryParams("search"))));
                } else {
                    map.put("articles", articleDao.findAll());
                    map.put("books", bookDao.findAll());
                    map.put("inproceedings", inproceedingDao.findAll());
                }
            } else {
                map.put("articles", articleDao.findAll());
                map.put("books", bookDao.findAll());
                map.put("inproceedings", inproceedingDao.findAll());
            }
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/article", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "article");
        }, new ThymeleafTemplateEngine());

        get("/book", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "book");
        }, new ThymeleafTemplateEngine());

        get("/inproceeding", (req, res) -> {
            HashMap map = new HashMap<>();
            return new ModelAndView(map, "inproceeding");
        }, new ThymeleafTemplateEngine());

        post("/article", new AddArticle(articleDao));
        post("/book", new AddBook(bookDao));
        post("/inproceeding", new AddInproceeding(inproceedingDao));

        get("/article/:id", new ViewArticle(articleDao));
        get("/book/:id", new ViewBook(bookDao));
        get("/inproceeding/:id", new ViewInproceeding(inproceedingDao));

        post("/edit_article", new EditArticle(articleDao));
        post("/edit_book", new EditBook(bookDao));
        post("/edit_inproceeding", new EditInproceeding(inproceedingDao));

        get("/delete_article/:id", (req, res) -> {
            articleDao.delete(req.params("id"));
            res.redirect("/");
            return "";
        });

        get("/delete_book/:id", (req, res) -> {
            bookDao.delete(req.params("id"));
            res.redirect("/");
            return "";
        });

        get("/delete_inproceeding/:id", (req, res) -> {
            inproceedingDao.delete(req.params("id"));
            res.redirect("/");
            return "";
        });

        post("/generatebibtex", (req, res) -> {
            String filename = "" + req.queryParams("filename");
            String[] articles = req.queryParamsValues("checked_articles");
            String[] books = req.queryParamsValues("checked_books");
            String[] inproceedings = req.queryParamsValues("checked_inproceedings");
            if (filename.length() == 0) {
                res.redirect("/");
                return "";
            } else {
                filename = filename.replaceAll("\\.bib$", "") + ".bib";

                BibtexGenerator gen = new BibtexGenerator();
                res.type("application/x-bibtex");
                res.header("Content-Disposition", String.format("attachment; filename=%s", filename));
                return gen.generateBibtexFile(referenceDao.findAll(articles, books, inproceedings));
            }
        });

        ACMImportController acmController = new ACMImportController(articleDao, bookDao, inproceedingDao);
        get("/acmimport", acmController);
        post("/acmimport", acmController);
    }
}
