package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.BookDao;
import sprintterit.models.Book;

public class ViewBook implements Route {

    private final BookDao dao;

    public ViewBook(BookDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        String id = req.params("id");
        Book book = dao.findOne(id);
        if (book == null) {
            return "No book with id: " + id;
        }

        Parameters parameters = new Parameters();

        parameters.put("id", book.getId());
        parameters.put("tags", book.getTags());
        parameters.put("authors", book.getAuthorsSeparated());
        parameters.put("title", book.getTitle());
        parameters.put("year", book.getYear());
        parameters.put("month", book.getMonth());
        parameters.put("volume", book.getVolume());
        parameters.put("series", book.getSeries());
        parameters.put("edition", book.getEdition());
        parameters.put("publisher", book.getPublisher());
        parameters.put("address", book.getAddress());
        parameters.put("note", book.getNote());
        parameters.put("key", book.getKey());

        HashMap map = new HashMap();
        map.putAll(parameters.getMap());
        return new ThymeleafTemplateEngine().render(
                new ModelAndView(map, "edit_book"));
    }

}
