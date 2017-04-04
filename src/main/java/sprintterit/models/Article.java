package sprintterit.models;

/**
 * We'll probably want to allow (some) null values in attributes (to signify
 * fields which have no value)
 */
public class Article extends Reference {

    private String journal;
    private int volume;
    private int number;
    private Pages pages;
    private String publisher;
    private String address;

    public Article(String id,
            Authors authors,
            String title,
            String journal,
            int volume,
            int number,
            Pages pages,
            int year) {
        this(id, authors, title, journal, volume, number, pages, year, null, null);
    }

    public Article(String id,
            Authors authors,
            String title,
            String journal,
            int volume,
            int number,
            Pages pages,
            int year,
            String publisher,
            String address) {
        super(id, authors, title, year);
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.pages = pages;
        this.publisher = publisher;
        this.address = address;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
