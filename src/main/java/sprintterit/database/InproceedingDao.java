package sprintterit.database;

import sprintterit.models.Inproceeding;
import sprintterit.models.Pages;

import java.sql.SQLException;
import java.util.List;

public class InproceedingDao {

    private Database database;
    private final QueryRunner<Inproceeding> query;

    public InproceedingDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new InproceedingCollector());
    }

    public void addInproceeding(String id, String authors, String title,
            Integer year, String booktitle, String editor, Integer volume, String series,
            String month, Pages pages, String organization,
            String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "INSERT INTO Reference (id, authors, title, year)"
                + "VALUES (?, ?, ?, ?)",
                id, authors, title, year);
        query.insert(
                "INSERT INTO Inproceedings (booktitle, startpage, endpage, publisher, "
                + "address, editor, volume, series, month, organization, "
                + "note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                booktitle,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, editor, volume, series, month,
                organization, note, key, id);
    }

    public Inproceeding findOne(String id) throws SQLException {
        return query.queryObject(
                "SELECT * FROM Reference r INNER JOIN Inproceedings a "
                + "ON r.id = a.id WHERE r.id = ?", id);
    }

    public List<Inproceeding> findAll() throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Inproceedings a ON r.id = a.id");
    }

    public void editInproceeding(String id, String authors, String title, Integer year, String booktitle, String editor, Integer volume, String series, String month, Pages pages, String organization, String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, title = ?, year = ? WHERE id = ?", authors, title, year, id);
        query.insert(
                "UPDATE Inproceedings SET booktitle = ?, startpage = ?, endpage = ?, publisher = ?, address = ?, editor = ?, volume = ?, series = ?, month = ?, organization = ?, note = ?, key = ? WHERE id = ?",
                booktitle,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, editor, volume, series, month, organization, note, key, id);
    }
}
