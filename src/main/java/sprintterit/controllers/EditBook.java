package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.BookDao;

public class EditBook implements Route {

    private final BookDao dao;

    public EditBook(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        SparkRequest request = new SparkRequest(req);
        InputValidator input = new InputValidator(request);

        String id = input.getString("id", "Id", false);
        String tags = input.getString("tags", "Tags", true);
        String authors = input.getString("authors", "Authors", true);
        String title = input.getString("title", "Title", true);
        Integer year = input.getInteger("year", "Year", true);
        String month = input.getString("month", "Month", false);
        Integer volume = input.getInteger("volume", "Volume", false);
        String series = input.getString("series", "Series", false);
        String edition = input.getString("edition", "Edition", false);
        String publisher = input.getString("publisher", "Publisher", true);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.put("errors", input.getErrors());
            map.putAll(input.getParameters());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "edit_book"));
        }

        dao.editBook(id, tags, authors, title, year, publisher, address,
                volume, series, edition, month, note, key);
        res.redirect("/");
        return "";
    }

}
