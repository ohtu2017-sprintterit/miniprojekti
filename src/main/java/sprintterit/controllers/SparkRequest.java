package sprintterit.controllers;

import spark.Request;

public class SparkRequest implements Input {

    private final Request req;

    public SparkRequest(Request req) {
        this.req = req;
    }

    @Override
    public String getParameter(String name) {
        return req.queryParams(name);
    }

}
