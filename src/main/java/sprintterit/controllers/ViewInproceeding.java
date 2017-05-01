package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.InproceedingDao;
import sprintterit.models.Inproceeding;

public class ViewInproceeding implements Route {

    private final InproceedingDao dao;

    public ViewInproceeding(InproceedingDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        String id = req.params("id");
        Inproceeding inproceeding = dao.findOne(id);
        if (inproceeding == null) {
            return "No inproceeding with id: " + id;
        }

        Parameters parameters = new Parameters();

        parameters.put("id", inproceeding.getId());
        parameters.put("tags", inproceeding.getTags());
        parameters.put("authors", inproceeding.getAuthorsSeparated());
        parameters.put("title", inproceeding.getTitle());
        parameters.put("booktitle", inproceeding.getBooktitle());
        parameters.put("editor", inproceeding.getEditor());
        parameters.put("volume", inproceeding.getVolume());
        parameters.put("series", inproceeding.getSeries());
        parameters.put("month", inproceeding.getMonth());
        parameters.put("year", inproceeding.getYear());
        parameters.put("startpage", inproceeding.getStartpage());
        parameters.put("endpage", inproceeding.getEndpage());
        parameters.put("organization", inproceeding.getOrganization());
        parameters.put("publisher", inproceeding.getPublisher());
        parameters.put("address", inproceeding.getAddress());
        parameters.put("note", inproceeding.getNote());
        parameters.put("key", inproceeding.getKey());

        HashMap map = new HashMap();
        map.putAll(parameters.getMap());
        return new ThymeleafTemplateEngine().render(
                new ModelAndView(map, "edit_inproceeding"));
    }

}
