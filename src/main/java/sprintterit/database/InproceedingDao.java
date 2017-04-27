package sprintterit.database;

import sprintterit.models.Inproceeding;
import sprintterit.models.Pages;

import java.sql.SQLException;
import java.util.List;

public class InproceedingDao {

    private Database database;
    private final QueryRunner<Inproceeding> query;
    private final BibtexKeyGen keygen;

    public InproceedingDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new InproceedingCollector());
        this.keygen = new BibtexKeyGen(database);
    }

    public void addInproceeding(String tags, String authors, String title,
            Integer year, String booktitle, String editor, Integer volume, String series,
            String month, Pages pages, String organization,
            String publisher, String address, String note, String key) throws SQLException {
        String bibKey = keygen.bibtexKey(authors, year);
        query.insert(
                "INSERT INTO Reference (id, tags, authors, title, year)"
                + "VALUES (?, ?, ?, ?, ?)",
                bibKey, tags, authors, title, year);
        query.insert(
                "INSERT INTO Inproceedings (booktitle, startpage, endpage, publisher, "
                + "address, editor, volume, series, month, organization, "
                + "note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                booktitle,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, editor, volume, series, month,
                organization, note, key, bibKey);
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

    public void editInproceeding(String id, String authors, String tags, String title, Integer year, String booktitle, String editor, Integer volume, String series, String month, Pages pages, String organization, String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, tags = ?, title = ?, year = ? WHERE id = ?", authors, tags, title, year, id);
        query.insert(
                "UPDATE Inproceedings SET booktitle = ?, startpage = ?, endpage = ?, publisher = ?, address = ?, editor = ?, volume = ?, series = ?, month = ?, organization = ?, note = ?, key = ? WHERE id = ?",
                booktitle,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, editor, volume, series, month, organization, note, key, id);
    }

    public void delete(String id) throws SQLException {
        query.insert("DELETE FROM Inproceedings WHERE id = ?", id);
        query.insert("DELETE FROM Reference WHERE id = ?", id);
    }
    
}
