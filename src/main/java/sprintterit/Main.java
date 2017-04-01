package sprintterit;

import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.Database;

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

        post("/article", (req, res) -> {
            String id = req.queryParams("id");
            String authors = req.queryParams("authors");
            String title = req.queryParams("title");
            String journal = req.queryParams("journal");
            String pages = req.queryParams("pages");
            String publisher = req.queryParams("publisher");
            String address = req.queryParams("address");
            int volume = Integer.parseInt("0" + req.queryParams("volume"));
            int number = Integer.parseInt("0" + req.queryParams("number"));
            int year = Integer.parseInt("0" + req.queryParams("year"));

            articleDao.addArticle(id, authors, title, year, journal, volume, number, pages, publisher, address);
            res.redirect("/");
            return "";
        });

        post("/book", (req, res) -> {
            String id = req.queryParams("id");
            String authors = req.queryParams("authors");
            String title = req.queryParams("title");
            String publisher = req.queryParams("publisher");
            String address = req.queryParams("address");
            int year = Integer.parseInt("0" + req.queryParams("year"));

            bookDao.addBook(id, authors, title, year, publisher, address);
            res.redirect("/");
            return "";
        });
    }
}
