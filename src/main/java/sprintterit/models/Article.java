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
    private int volume;
    private int number;
    private String month;
    private Pages pages;
    private String publisher;
    private String address;
    private String note;
    private String key;

 //    public Article(String id,
//            Authors authors,
//            String title,
//            String journal,
//            int volume,
//            int number,
//            Pages pages,
//            int year) {
//        this(id, authors, title, journal, volume, number, pages, year, null, null);
//    }
    public Article (String id,
            Authors authors,
            String title,
            String journal,
            int volume,
            int number,
            String month,
            Pages pages,
            int year,
            String publisher,
            String address,
            String note,
            String key) {
        super(id, authors, title, year);
        this.journal = journal;
        this.volume = volume;
        this.number = number;
        this.month = month;
        this.pages = pages;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
                "  year = \"" + getYear() + "\",\n",
                getVolume() == 0 ? "" : "  volume = \"" + getVolume() + "\",\n",
                getNumber() == 0 ? "" : "  number = \"" + getNumber() + "\",\n",
                pages == null ? "" : "  pages = \"" + pages.toString() + "\",\n",
                month == null ? "" : "  month = \"" + month + "\",\n",
                note == null ? "" : "  note = \"" + note + "\",\n",
                "}\n"
        );
    }
}
