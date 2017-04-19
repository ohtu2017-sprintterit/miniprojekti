package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;

public class AddArticle implements Route {

    private final ArticleDao dao;

    public AddArticle(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        InputValidator input = new InputValidator(req);

        String id = input.getString("id", "BibTeX key", true);
        String authors = input.getString("authors", "Authors", true);
        String title = input.getString("title", "Title", true);
        String journal = input.getString("journal", "Journal", true);
        Integer volume = input.getInteger("volume", "Volume", false);
        Integer number = input.getInteger("number", "Number", false);
        String month = input.getString("month", "Month", false); // not on the form
        Integer year = input.getInteger("year", "Year", true);
        Integer startpage = input.getInteger("startpage", "Startpage", false);
        Integer endpage = input.getInteger("endpage", "Endpage", false);
        String publisher = input.getString("publisher", "Publisher", false);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (dao.findOne(id) != null) {
            input.addError("id", "BibTeX key is not unique (already taken by another article)");
        }

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.putAll(input.getParameters());
            map.put("errors", input.getErrors());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "article"));
        }

        dao.addArticle(id, authors, title, year, journal, volume, month, number,
                startpage, endpage, publisher, address, note, key);
        res.redirect("/");
        return "";
    }

}
