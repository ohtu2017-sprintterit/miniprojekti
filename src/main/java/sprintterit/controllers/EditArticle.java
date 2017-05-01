package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.models.Pages;

public class EditArticle implements Route {

    private final ArticleDao dao;

    public EditArticle(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        SparkRequest request = new SparkRequest(req);
        InputValidator input = new InputValidator(request);

        String id = input.getString("id", "Id", false);
        String tags = input.getString("tags", "Tags", false);
        String authors = input.getString("authors", "Authors", true);
        String title = input.getString("title", "Title", true);
        String journal = input.getString("journal", "Journal", true);
        Integer volume = input.getInteger("volume", "Volume", false);
        Integer number = input.getInteger("number", "Number", false);
        String month = input.getString("month", "Month", false); // not on the form
        Integer year = input.getInteger("year", "Year", true);
        Pages pages = input.getPages("startpage", "Startpage", "endpage", "Endpage");
        String publisher = input.getString("publisher", "Publisher", false);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.put("errors", input.getErrors());
            map.putAll(input.getParameters());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "edit_article"));
        }

        dao.editArticle(id, tags, authors, title, year, journal, volume, month, number,
                pages, publisher, address, note, key);
        res.redirect("/");
        return "";
    }

}
