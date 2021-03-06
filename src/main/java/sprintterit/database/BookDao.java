package sprintterit.database;

import sprintterit.models.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDao implements DaoWithDelete {

    private final Database database;
    private final QueryRunner<Book> query;
    private final BibtexKeyGen keygen;

    public BookDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new BookCollector());
        this.keygen = new BibtexKeyGen(new CheckDatabaseId(database));
    }

    public void addBook(String tags, String authors, String title, Integer year,
            String publisher, String address, Integer volume, String series,
            String edition, String month, String note, String key) throws SQLException {
        String bibKey = keygen.bibtexKey(authors, year);
        query.insert(
                "INSERT INTO Reference (id, tags, authors, title, year) VALUES (?, ?, ?, ?, ?)", bibKey, tags, authors, title, year);
        query.insert(
                "INSERT INTO Book (publisher, address, volume, series, edition, month, note, key, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                publisher, address, volume, series, edition, month, note, key, bibKey);
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

    public void editBook(String id, String tags, String authors, String title, Integer year, String publisher, String address, Integer volume, String series, String edition, String month, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, tags = ?, title = ?, year = ? WHERE id = ?",
                authors, tags, title, year, id);
        query.insert(
                "UPDATE Book SET publisher = ?, address = ?, volume = ?, series = ?, edition = ?, month = ?, note = ?, key = ? WHERE id = ?",
                publisher, address, volume, series, edition, month, note, key, id);
    }

    @Override
    public void delete(String id) throws SQLException {
        if (findOne(id) == null) {
            return;
        }

        query.insert("DELETE FROM Book WHERE id = ?", id);
        query.insert("DELETE FROM Reference WHERE id = ?", id);
    }

    public List<Book> findTag(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Book a ON r.id = a.id WHERE lower(r.tags) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Book> findAuthor(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Book a ON r.id = a.id WHERE lower(r.authors) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Book> findTitle(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Book a ON r.id = a.id WHERE lower(r.title) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Book> findYear(int year) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Book a ON r.id = a.id WHERE r.year = ?", year);
    }

}
