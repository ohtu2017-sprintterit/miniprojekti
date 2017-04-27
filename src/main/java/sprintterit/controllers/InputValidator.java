package sprintterit.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import sprintterit.models.Pages;

public class InputValidator {

    private final Input input;
    private final Map<String, String> errors;
    private final Map<String, String> parameters;

    public InputValidator(Input input) {
        this.input = Objects.requireNonNull(input);
        this.errors = new LinkedHashMap<>();
        this.parameters = new HashMap<>();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public boolean isOk() {
        return errors.isEmpty();
    }

    public void addError(String name, String errorMessage) {
        errors.put(name, errorMessage);
    }

    boolean hasError(String name) {
        return errors.containsKey(name);
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getString(String name, String fullname, boolean isRequired) {
        String value = input.getParameter(name);
        if (value != null) {
            value = value.trim();
            save(name, value);
        }

        if (isRequired && isEmpty(value)) {
            addError(name, fullname + " is required");
        }

        return value;
    }

    public Integer getInteger(String name, String fullname, boolean isRequired) {
        String str = getString(name, fullname, isRequired);
        if (isEmpty(str)) {
            return null;
        }

        Integer value = parseInt(str);
        if (value == null) {
            addError(name, fullname + " should be an integer");
        }

        return value;
    }

    public Pages getPages(String beginName, String beginFullname, String endName, String endFullname) {
        PagesValidator p = new PagesValidator(beginName, beginFullname, endName, endFullname);
        return p.getPages(this);
    }

    private boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }

    private Integer parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void save(String name, String value) {
        parameters.put(name, value);
    }

}
