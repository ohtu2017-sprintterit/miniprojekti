package sprintterit.models;

/**
 * We'll probably want to allow (some) null values in attributes (to signify
 * fields which have no value)
 * 
 * >>> At this point empty values are empty strings, is there a need for
 * null values?
 * 
 */
public class Article extends Reference {

    private String journal;
    private Integer volume;
    private Integer number;
    private String month;
    private Integer startpage;
    private Integer endpage;
    private String publisher;
    private String address;
    private String note;
    private String key;

    public Article (String id,
            Authors authors,
            String title,
            String journal,
            Integer volume,
            Integer number,
            String month,
            Integer startpage,
            Integer endpage,
            Integer year,
            String publisher,
            String address,
            String note,
            String key) {
        super(id, authors, title, year);
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.month = month;
        this.startpage = startpage;
        this.endpage = endpage;
        this.publisher = publisher;
        this.address = address;
        this.note = note;
        this.key = key;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getStartpage() {
        return startpage;
    }

    public void setStartpage(Integer startpage) {
        this.startpage = startpage;
    }

    public Integer getEndpage() {
        return endpage;
    }

    public void setEndpage(Integer endpage) {
        this.endpage = endpage;
    }

    public String getPages() {
        if (startpage == null || endpage == null) {
            return null;
        }

        return startpage + "--" + endpage;
    }

    public void setPages(Integer startpage, Integer endpage) {
        this.startpage = startpage;
        this.endpage = endpage;
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
                "@article{" + getId() + ",\n",
                "  author = {" + getAuthors().toString() + "},\n",
                "  title = {" + getTitle() + "},\n",
                "  journal = {" + getJournal() + "},\n",
                "  year = \"" + getYear() + "\",\n",
                getVolume() == null ? "" : "  volume = \"" + getVolume() + "\",\n",
                getNumber() == null ? "" : "  number = \"" + getNumber() + "\",\n",
                getPages() == null ? "" : "  pages = \"" + getPages() + "\",\n",
                (getMonth() == null || getMonth().length() == 0) ? "" : "  month = " + getMonth() + ",\n",
                (getNote() == null || getNote().length() == 0) ? "": "  note = \"" + getNote() + "\",\n",
                (getKey() == null || getKey().length() == 0) ? "" : "  key = {" + getKey() + "}\n",
                "}\n"
        ).replaceAll(",(?=\n})", "");
    }
}
