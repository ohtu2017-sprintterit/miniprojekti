package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import sprintterit.database.ArticleDao;
import sprintterit.database.BookDao;
import sprintterit.database.InproceedingDao;

/**
 * Shows either the index page or search results.
 * 
 */
public class GetIndex implements Route {

    private final ArticleDao articleDao;
    private final BookDao bookDao;
    private final InproceedingDao inproceedingDao;

    public GetIndex(ArticleDao articleDao, BookDao bookDao, InproceedingDao inproceedingDao) {
        this.articleDao = articleDao;
        this.bookDao = bookDao;
        this.inproceedingDao = inproceedingDao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        HashMap map = new HashMap<>();
        if (req.queryParams("search") != null) {
            if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("tag")) {
                map.put("articles", articleDao.findTag(req.queryParams("search")));
                map.put("books", bookDao.findTag(req.queryParams("search")));
                map.put("inproceedings", inproceedingDao.findTag(req.queryParams("search")));
            } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("author")) {
                map.put("articles", articleDao.findAuthor(req.queryParams("search")));
                map.put("books", bookDao.findAuthor(req.queryParams("search")));
                map.put("inproceedings", inproceedingDao.findAuthor(req.queryParams("search")));
            } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("title")) {
                map.put("articles", articleDao.findTitle(req.queryParams("search")));
                map.put("books", bookDao.findTitle(req.queryParams("search")));
                map.put("inproceedings", inproceedingDao.findTitle(req.queryParams("search")));
            } else if (req.queryParams("searchby") != null && req.queryParams("searchby").equals("year")
                    && req.queryParams("search").matches("^[0-9]+$")) {
                map.put("articles", articleDao.findYear(Integer.parseInt(req.queryParams("search"))));
                map.put("books", bookDao.findYear(Integer.parseInt(req.queryParams("search"))));
                map.put("inproceedings", inproceedingDao.findYear(Integer.parseInt(req.queryParams("search"))));
            } else {
                map.put("articles", articleDao.findAll());
                map.put("books", bookDao.findAll());
                map.put("inproceedings", inproceedingDao.findAll());
            }
        } else {
            map.put("articles", articleDao.findAll());
            map.put("books", bookDao.findAll());
            map.put("inproceedings", inproceedingDao.findAll());
        }

        return new ThymeleafTemplateEngine().render(
                new ModelAndView(map, "index"));
    }

}
