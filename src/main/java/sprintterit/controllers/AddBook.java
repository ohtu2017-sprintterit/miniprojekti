package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.BookDao;

public class AddBook implements Route {

    private final BookDao dao;

    public AddBook(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        InputValidator input = new InputValidator(req);

        String id = input.getString("id", "BibTeX key", true);
        String authors = input.getString("authors", "Authors", true);
        String title = input.getString("title", "Title", true);
        Integer year = input.getInteger("year", "Year", true);
        String month = input.getString("month", "Month", false); // not on the form
        Integer volume = input.getInteger("volume", "Volume", false);
        String series = input.getString("series", "Series", false);
        String edition = input.getString("edition", "Edition", false);
        String publisher = input.getString("publisher", "Publisher", true);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

        if (dao.findOne(id) != null) {
            input.addError("id", "BibTeX key is not unique (already taken by another reference)");
        }

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.putAll(input.getParameters());
            map.put("errors", input.getErrors());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "book"));
        }

        // this is a quick workaround, BookDao uses ints (instead of Integers which can be null)
        if (volume == null) volume = 0;

        dao.addBook(id, authors, title, year, publisher, address,
                volume, series, edition, month, note, key);
        res.redirect("/");
        return "";
    }

}
