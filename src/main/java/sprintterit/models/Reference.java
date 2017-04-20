package sprintterit.models;

import java.util.Objects;

/**
 * We'll probably want to allow (some) null values in attributes (to signify
 * fields which have no value)
 */
public abstract class Reference {

    private final String id;
    private Authors authors;
    private String title;
    private Integer year;

    protected Reference(String id, Authors authors, String title, Integer year) {
        this.id = id;
        this.authors = Objects.requireNonNull(authors);
        this.title = title;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public Authors getAuthors() {
        return authors;
    }

    public void setAuthors(Authors authors) {
        this.authors = Objects.requireNonNull(authors);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
