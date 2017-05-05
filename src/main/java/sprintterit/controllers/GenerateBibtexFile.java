package sprintterit.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sprintterit.bibtexgen.BibtexGenerator;
import sprintterit.database.ReferenceDao;

public class GenerateBibtexFile implements Route {

    private final ReferenceDao dao;

    public GenerateBibtexFile(ReferenceDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        String filename = "" + req.queryParams("filename");
        String[] articles = req.queryParamsValues("checked_articles");
        String[] books = req.queryParamsValues("checked_books");
        String[] inproceedings = req.queryParamsValues("checked_inproceedings");
        if (filename.length() == 0) {
            res.redirect("/");
            return "";
        } else {
            filename = filename.replaceAll("\\.bib$", "") + ".bib";

            BibtexGenerator gen = new BibtexGenerator();
            res.type("application/x-bibtex");
            res.header("Content-Disposition", String.format("attachment; filename=%s", filename));
            return gen.generateBibtexFile(dao.findAll(articles, books, inproceedings));
        }
    }

}
