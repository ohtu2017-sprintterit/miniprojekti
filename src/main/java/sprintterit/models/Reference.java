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
    private String tags;

    protected Reference(String id, Authors authors, String title, Integer year, String tags) {
        this.id = id;
        this.authors = Objects.requireNonNull(authors);
        this.title = title;
        this.year = year;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
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

    public String getAuthorsSeparated() {
        return authors.toString().replace(" and ", "\n");
    }

}
