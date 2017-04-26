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
        Integer begin = input.getInteger(beginName, beginFullname, false);
        Integer end = input.getInteger(endName, endFullname, false);
        int initialSize = input.getErrors().size();
        if (begin == null && end == null) {
            // No page numbers provided, this is ok
            return null;
        }

        check(begin, end, input);

        if (input.getErrors().size() > initialSize) {
            return null;
        }

        return new Pages(begin, end);
    }

    private void check(Integer begin, Integer end, InputValidator input) {
        if (begin == null) {
            input.addError(beginName, endFullname + " given but " + beginFullname + " is missing");
        }

        if (end == null) {
            input.addError(endName, beginFullname + " given but " + endFullname + " is missing");
        }

        if (notNull(begin, end) && end < begin) {
            input.addError(endName, endFullname + " is less than " + beginFullname);
        }

        if (notNull(begin) && begin < 1) {
            input.addError(beginName, beginFullname + " is less than 1");
        }
    }

    private boolean notNull(Integer page) {
        return page != null;
    }

    private boolean notNull(Integer begin, Integer end) {
        return begin != null && end != null;
    }

}
