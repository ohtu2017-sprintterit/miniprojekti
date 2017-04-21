package sprintterit.database;

import sprintterit.models.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDao {

    private Database database;
    private final QueryRunner<Book> query;

    public BookDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new BookCollector());
    }

    public void addBook(String id, String authors, String title, Integer year,
            String publisher, String address, Integer volume, String series,
            String edition, String month, String note, String key) throws SQLException {
        query.insert(
                "INSERT INTO Reference (id, authors, title, year) VALUES (?, ?, ?, ?)", id, authors, title, year);
        query.insert(
                "INSERT INTO Book (publisher, address, volume, series, edition, month, note, key, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                publisher, address, volume, series, edition, month, note, key, id);
    }

    public Book findOne(String id) throws SQLException {
        return query.queryObject(
                "SELECT * FROM Reference r INNER JOIN Book a "
                + "ON r.id = a.id WHERE r.id = ?", id);
    }

    public List<Book> findAll() throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Book a ON r.id = a.id");
    }

    public void editBook(String id, String authors, String title, Integer year, String publisher, String address, Integer volume, String series, String edition, String month, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, title = ?, year = ? WHERE id = ?",
                authors, title, year, id);
        query.insert(
                "UPDATE Book SET publisher = ?, address = ?, volume = ?, series = ?, edition = ?, month = ?, note = ?, key = ? WHERE id = ?",
                publisher, address, volume, series, edition, month, note, key, id);
    }
}
