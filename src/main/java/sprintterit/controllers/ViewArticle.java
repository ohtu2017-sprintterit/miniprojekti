package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.models.Article;

public class ViewArticle implements Route {

    private final ArticleDao dao;

    public ViewArticle(ArticleDao dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        String id = req.params("id");
        Article article = dao.findOne(id);
        if (article == null) {
            return "No article with id: " + id;
        }

        Parameters parameters = new Parameters();

        parameters.put("id", article.getId());
        parameters.put("tags", article.getTags());
        parameters.put("authors", article.getAuthorsSeparated());
        parameters.put("title", article.getTitle());
        parameters.put("journal", article.getJournal());
        parameters.put("volume", article.getVolume());
        parameters.put("number", article.getNumber());
        parameters.put("month", article.getMonth()); // not on the form
        parameters.put("year", article.getYear());
        parameters.put("startpage", article.getStartpage());
        parameters.put("endpage", article.getEndpage());
        parameters.put("publisher", article.getPublisher());
        parameters.put("address", article.getAddress());
        parameters.put("note", article.getNote());
        parameters.put("key", article.getKey());

        HashMap map = new HashMap();
        map.putAll(parameters.getMap());
        return new ThymeleafTemplateEngine().render(
                new ModelAndView(map, "edit_article"));
    }

}
