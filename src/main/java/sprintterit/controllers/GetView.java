package sprintterit.controllers;

import java.util.HashMap;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 * Renders the given view (with no parameters).
 *
 * This is a generic controller for those views which do not require any special
 * processing.
 */
public class GetView implements Route {

    private final String viewName;

    public GetView(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        HashMap map = new HashMap<>();
        return new ThymeleafTemplateEngine().render(
                new ModelAndView(map, viewName));
    }

}
