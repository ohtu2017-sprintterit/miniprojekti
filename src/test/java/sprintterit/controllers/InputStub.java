package sprintterit.controllers;

import java.util.Map;

public class InputStub implements Input {

    private Map<String, String> parameters;

    public InputStub(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

}
