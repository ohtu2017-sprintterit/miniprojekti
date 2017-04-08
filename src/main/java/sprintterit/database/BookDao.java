package sprintterit.database;

import sprintterit.models.Authors;
import sprintterit.models.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private Database database;

    public BookDao(Database database) {
        this.database = database;
    }

    public void addBook(String id, String authors, String title, int year,
            String publisher, String address, int volume, String series, 
            String edition, String month, String note, String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO "
                + "Reference (id, authors, title, year) VALUES (?, ?, ?, ?)");
        statement.setString(1, id);
        statement.setString(2, authors);
        statement.setString(3, title);
        statement.setInt(4, year);
        statement.execute();

        statement = connection.prepareStatement("INSERT INTO "
                + "Book (publisher, address, volume, series, "
                + "edition, month, note, key, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, publisher);
        statement.setString(2, address);
        statement.setInt(3, volume);
        statement.setString(4, series);
        statement.setString(5, edition);
        statement.setString(6, month);
        statement.setString(7, note);
        statement.setString(8, key);
        statement.setString(9, id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public Book findOne(String id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Reference r INNER JOIN Book b "
                        + "ON r.id = b.id WHERE r.id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if (!rs.isBeforeFirst()) {
            return null;
        }

        return buildBook(rs);
    }

    public List<Book> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Reference r INNER JOIN Book b "
                        + "ON r.id = b.id");
        ResultSet rs = statement.executeQuery();
        List<Book> books = new ArrayList<>();

        while (rs.next()) {
            books.add(buildBook(rs));
        }

        return books;
    }

    private Book buildBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getString("id"),
                new Authors(rs.getString("authors")),
                rs.getString("title"),
                rs.getInt("year"),
                rs.getString("publisher"),
                rs.getString("address"),
                rs.getInt("volume"),
                rs.getString("series"),
                rs.getString("edition"),
                rs.getString("month"),
                rs.getString("note"),
                rs.getString("key")
        );
    }
}
