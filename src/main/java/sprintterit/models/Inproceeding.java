package sprintterit.models;

public class Inproceeding extends Reference {

    private String booktitle;
    private Pages pages;
    private String publisher;
    private String address;
    private String editor;
    private int volume;
    private String series;
    private String month;
    private String organization;
    private String note;
    private String key;

    public Inproceeding(String id,
            Authors authors,
            String title,
            String booktitle,
            Pages pages,
            int year,
            String editor,
            String series,
            String month,
            String organization,
            String publisher,
            String address,
            String note,
            String key) {
        super(id, authors, title, year);
        this.booktitle = booktitle;
        this.pages = pages;
        this.editor = editor;
        this.series = series;
        this.month = month;
        this.organization = organization;
        this.publisher = publisher;
        this.address = address;
        this.note = note;
        this.key = key;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public Pages getPages() {
        return pages;
    }

    public void setPages(Pages pages) {
        this.pages = pages;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
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
                "@inproceedings{" + getId() + ",\n",
                "  author = {" + getAuthors().toString() + "},\n",
                "  title = {" + getTitle() + "},\n",
                "  booktitle = {" + getBooktitle() + "},\n",
                "  year = \"" + getYear() + "\",\n",
                (getEditor() == null || getEditor().length() == 0) ? "" : "  editor = {" + getEditor() + "},\n",
                (getSeries() == null || getSeries().length() == 0) ? "" : "  series = {" + getSeries() + "},\n",
                getPages().getEnd() == 0 ? "" : "  pages = {" + getPages().toString() + "},\n",
                (getAddress() == null || getAddress().length() == 0) ? "" : "  address = {" + getAddress() + "},\n",
                (getOrganization() == null || getOrganization().length() == 0) ? "" : "  organization = {" + getOrganization() + "},\n",
                (getPublisher() == null || getPublisher().length() == 0) ? "" : "  publisher = {" + getPublisher() + "},\n",
                (getMonth() == null || getMonth().length() == 0) ? "" : "  month = " + getMonth() + ",\n",
                (getNote() == null || getNote().length() == 0) ? "" : "  note = {" + getNote() + "},\n",
                (getKey() == null || getKey().length() == 0) ? "" : "  key = {" + getKey() + "},\n",
                "}\n"
        ).replaceAll(",(?=\n})", "");
    }
}
