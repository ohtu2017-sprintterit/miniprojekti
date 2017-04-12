package sprintterit.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import sprintterit.database.ArticleDao;

public class AddArticle implements TemplateViewRoute {

    private final ArticleDao dao;
    private Request req;
    private Map<String, String> queryParams;
    private Map<String, String> errors;

    public AddArticle(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public ModelAndView handle(Request req, Response res) throws Exception {
        this.req = req;
        this.queryParams = new HashMap<>();
        this.errors = new LinkedHashMap<>();

        String id = getString("id", true);
        String authors = getString("authors", true);
        String title = getString("title", true);
        Integer year = getInteger("year", true);
        String journal = getString("journal", true);

        Integer volume = getInteger("volume", false);
        Integer number = getInteger("number", false);
        String month = getString("month", false);
        Integer startpage = getInteger("startpage", false);
        Integer endpage = getInteger("endpage", false);
        String publisher = getString("publisher", false);
        String address = getString("address", false);
        String note = getString("note", false);
        String key = getString("key", false);

        if (!errors.isEmpty()) {
            HashMap map = new HashMap();
            map.putAll(queryParams);
            map.put("errors", errors);
            return new ModelAndView(map, "article");
        }

        // this is a quick workaround, the handling of nulls needs some work
        if (volume == null) volume = 0;
        if (number == null) number = 0;
        if (startpage == null) startpage = 0;
        if (endpage == null) endpage = 0;

        dao.addArticle(id, authors, title, year, journal,
                volume, month, number, startpage, endpage, publisher, address, note, key);
        res.redirect("/");
        return null;
    }

    private boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private String getString(String name, boolean isRequired) {
        String value = req.queryParams(name);
        if (value != null) {
            value = value.trim();
            queryParams.put(name, value);
        }

        if (isRequired && isEmpty(value)) {
            errors.put(name, "This value is required");
        }

        return value;
    }

    private Integer getInteger(String name, boolean isRequired) {
        String str = getString(name, isRequired);
        if (isEmpty(str)) {
            return null;
        }

        Integer value = parseInt(str);
        if (value == null) {
            errors.put(name, "Not an integer");
        }

        return value;
    }

    private Integer parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
