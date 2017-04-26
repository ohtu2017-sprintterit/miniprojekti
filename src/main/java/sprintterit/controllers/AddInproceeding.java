package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.InproceedingDao;
import sprintterit.models.Pages;

public class AddInproceeding implements Route {

    private final InproceedingDao dao;

    public AddInproceeding(InproceedingDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        SparkRequest request = new SparkRequest(req);
        InputValidator input = new InputValidator(request);

        String tags = input.getString("tags", "Tags", false);
        String authors = input.getString("authors", "Authors", true);
        String title = input.getString("title", "Title", true);
        String booktitle = input.getString("booktitle", "Booktitle", true);
        String editor = input.getString("editor", "Editor", false);
        Integer volume = input.getInteger("volume", "Volume", false);
        String series = input.getString("series", "Series", false);
        String month = input.getString("month", "Month", false);
        Integer year = input.getInteger("year", "Year", true);
        Pages pages = input.getPages("startpage", "Startpage", "endpage", "Endpage");
        String organization = input.getString("organization", "Organization", false);
        String publisher = input.getString("publisher", "Publisher", false);
        String address = input.getString("address", "Address", false);
        String note = input.getString("note", "Note", false);
        String key = input.getString("key", "Key", false);

//        if (dao.findOne(id) != null) {
//            input.addError("id", "BibTeX key is not unique (already taken by another reference)");
//        }

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.putAll(input.getParameters());
            map.put("errors", input.getErrors());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "inproceeding"));
        }

        dao.addInproceeding(tags, authors, title, year, booktitle,
                editor, volume, series, month, pages,
                organization, publisher, address, note, key);
        res.redirect("/");
        return "";
    }

}
