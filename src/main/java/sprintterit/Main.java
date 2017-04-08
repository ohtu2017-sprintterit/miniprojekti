package sprintterit;

import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.Database;
import sprintterit.database.InproceedingDao;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Osoite: http://osoitetuleetahan.herokuapp.com/
        // Paikallinen versio: http://localhost:4567/

        // Asetetaan portti, jos heroku antaa PORT-ympäristömuuttujan
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        // Käytetään oletuksena paikallista sqlite-tietokantaa
        // Jos heroku antaa käyttöömme tietokantaosoitteen, otetaan se käyttöön
        String jdbcOsoite = "jdbc:sqlite:tietokantatiedosto.db";
        if (System.getenv("JDBC_DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("JDBC_DATABASE_URL");
        }
        Database database = new Database(jdbcOsoite);
        database.init();

        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        InproceedingDao inproceedingDao = new InproceedingDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("title", "Viitteidenhallinta");

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

        post("/article", (req, res) -> {
            String id = req.queryParams("id");
            String authors = req.queryParams("authors");
            String title = req.queryParams("title");
            int year = Integer.parseInt("0" + req.queryParams("year"));
            String journal = req.queryParams("journal");
            int volume = Integer.parseInt("0" + req.queryParams("volume"));
            int number = Integer.parseInt("0" + req.queryParams("number"));
            String month = req.queryParams("month");
            int startpage = Integer.parseInt("0" + req.queryParams("startpage"));
            int endpage = Integer.parseInt("0" + req.queryParams("endpage"));
            String publisher = req.queryParams("publisher");
            String address = req.queryParams("address");
            String note = req.queryParams("note");
            String key = req.queryParams("key");

            articleDao.addArticle(id, authors, title, year, journal,
                    volume, month, number, startpage, endpage, publisher, address, note, key);
            res.redirect("/");
            return "";
        });

        post("/book", (req, res) -> {
            String id = req.queryParams("id");
            String authors = req.queryParams("authors");
            String title = req.queryParams("title");
            int year = Integer.parseInt("0" + req.queryParams("year"));
            String month = req.queryParams("month");
            int volume = Integer.parseInt("0" + req.queryParams("volume"));
            String series = req.queryParams("series");
            String edition = req.queryParams("edition");
            String publisher = req.queryParams("publisher");
            String address = req.queryParams("address");
            String note = req.queryParams("note");
            String key = req.queryParams("key");

            bookDao.addBook(id, authors, title, year, publisher, address,
                    volume, series, edition, month, note, key);
            res.redirect("/");
            return "";
        });

        post("/inproceeding", (req, res) -> {
            String id = req.queryParams("id");
            String authors = req.queryParams("authors");
            String title = req.queryParams("title");
            String booktitle = req.queryParams("booktitle");
            String editor = req.queryParams("editor");
            int volume = Integer.parseInt("0" + req.queryParams("volume"));
            String series = req.queryParams("series");
            String month = req.queryParams("month");
            int year = Integer.parseInt("0" + req.queryParams("year"));
            int startpage = Integer.parseInt("0" + req.queryParams("startpage"));
            int endpage = Integer.parseInt("0" + req.queryParams("endpage"));
            String organization = req.queryParams("organization");
            String publisher = req.queryParams("publisher");
            String address = req.queryParams("address");
            String note = req.queryParams("note");
            String key = req.queryParams("key");

            inproceedingDao.addInproceeding(id, authors, title, year, booktitle, 
                    editor, volume, series, month, startpage, endpage, 
                    organization, publisher, address, note, key);
            res.redirect("/");
            return "";
        });
    }
}
