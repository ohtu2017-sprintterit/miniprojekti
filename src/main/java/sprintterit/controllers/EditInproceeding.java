package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.InproceedingDao;
import sprintterit.models.Pages;

public class EditInproceeding implements Route {

    private final InproceedingDao dao;

    public EditInproceeding(InproceedingDao dao) {
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

        if (!input.isOk()) {
            HashMap map = new HashMap();
            map.put("errors", input.getErrors());
            map.putAll(input.getParameters());
            return new ThymeleafTemplateEngine().render(
                    new ModelAndView(map, "edit_inproceeding"));
        }

        dao.editInproceeding(id, tags, authors, title, year, booktitle,
                editor, volume, series, month, pages,
                organization, publisher, address, note, key);
        res.redirect("/");
        return "";
    }

}
