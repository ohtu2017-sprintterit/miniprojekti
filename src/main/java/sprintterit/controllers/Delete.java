package sprintterit.controllers;

import spark.Request;
import spark.Response;
import spark.Route;
import sprintterit.database.DaoWithDelete;

/**
 * Delete works with the specialized DAO classes: ArticleDao/BookDao/InproceedingDao
 */
public class Delete implements Route {

    private final DaoWithDelete dao;

    public Delete(DaoWithDelete dao) {
        this.dao = dao;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        // The DAO checks the id before acting on it
        dao.delete(req.params("id"));
        res.redirect("/");
        return "";
    }

}
