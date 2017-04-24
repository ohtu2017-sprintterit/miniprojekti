package sprintterit.models;

public class Book extends Reference {

    private String publisher;
    private String address;
    private Integer volume;
    private String series;
    private String edition;
    private String month;
    private String note;
    private String key;

    public Book(String id,
            Authors authors,
            String title,
            Integer year,
            String publisher,
            String address,
            Integer volume,
            String series,
            String edition,
            String month,
            String note,
            String key) {
        super(id, authors, title, year);
        this.publisher = publisher;
        this.address = address;
        this.volume = volume;
        this.series = series;
        this.edition = edition;
        this.month = month;
        this.note = note;
        this.key = key;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String journal) {
        this.publisher = journal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return String.join(
                "",
                "@book{" + getId() + ",\n",
                "  author = {" + getAuthors().toString() + "},\n",
                "  title = {" + getTitle() + "},\n",
                "  publisher = {" + getPublisher() + "},\n",
                "  year = \"" + getYear() + "\",\n",
                getVolume() == null || getVolume() == 0 ? "" : "  volume = \"" + getVolume() + "\",\n",
                (getSeries() == null || getSeries().length() == 0) ? "" : "  series = \"" + getSeries() + "\",\n",
                (getAddress() == null || getAddress().length() == 0) ? "" : "  address = \"" + getAddress() + "\",\n",
                (getEdition() == null || getEdition().length() == 0) ? "" : "  edition = \"" + getEdition() + "\",\n",
                (getMonth() == null || getMonth().length() == 0) ? "" : "  month = " + getMonth() + ",\n",
                (getNote() == null || getNote().length() == 0) ? "" : "  note = {" + getNote() + "},\n",
                (getKey() == null || getKey().length() == 0) ? "" : "  key = {" + getKey() + "},\n",
                "}\n"
        ).replaceAll(",(?=\n})", "");
    }
}
