package sprintterit;

import java.sql.SQLException;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

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
        String jdbcOsoite = "jdbc:sqlite:tietokannat/tietokantatiedosto.db";
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

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
    }
}
