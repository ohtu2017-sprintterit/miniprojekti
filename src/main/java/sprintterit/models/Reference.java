package sprintterit.models;

import java.util.Objects;

/**
 * We'll probably want to allow (some) null values in attributes (to signify
 * fields which have no value)
 */
public abstract class Reference {

    private final int id;
    private Authors authors;
    private String title;
    private int year;

    protected Reference(int id, Authors authors, String title, int year) {
        this.id = id;
        this.authors = Objects.requireNonNull(authors);
        this.title = title;
        this.year = year;
    }

    public int getId() {
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
