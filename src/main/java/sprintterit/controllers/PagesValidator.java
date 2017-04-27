package sprintterit.controllers;

import sprintterit.models.Pages;

public class PagesValidator {

    private final String beginName;
    private final String beginFullname;
    private final String endName;
    private final String endFullname;

    public PagesValidator(String beginName, String beginFullname, String endName, String endFullname) {
        this.beginName = beginName;
        this.beginFullname = beginFullname;
        this.endName = endName;
        this.endFullname = endFullname;
    }

    public Pages getPages(InputValidator input) {
        int initialSize = input.getErrors().size();

        // "input" checks if these values are really integers
        Integer begin = input.getInteger(beginName, beginFullname, false);
        Integer end = input.getInteger(endName, endFullname, false);

        // We check a few others things as well
        check(begin, end, input);

        if (input.getErrors().size() > initialSize) {
            return null;
        }

        return new Pages(begin, end);
    }

    private void check(Integer begin, Integer end, InputValidator input) {
        if (begin == null && end == null) {
            return;
        }

        if (begin == null) {
            addError(input, beginName, endFullname + " given but " + beginFullname + " is missing");
        }

        if (end == null) {
            addError(input, endName, beginFullname + " given but " + endFullname + " is missing");
        }

        if (notNull(begin, end) && end < begin) {
            addError(input, endName, endFullname + " is less than " + beginFullname);
        }

        if (notNull(begin) && begin < 1) {
            addError(input, beginName, beginFullname + " is less than 1");
        }
    }

    private void addError(InputValidator input, String name, String msg) {
        if (input.hasError(name)) {
            return;
        }

        input.addError(name, msg);
    }

    private boolean notNull(Integer page) {
        return page != null;
    }

    private boolean notNull(Integer begin, Integer end) {
        return begin != null && end != null;
    }

}
