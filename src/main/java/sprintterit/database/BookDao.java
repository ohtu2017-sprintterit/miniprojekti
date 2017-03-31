package sprintterit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDao {

    private Database database;

    public BookDao(Database database) {
        this.database = database;
    }

    public void addBook(String id, String authors, String title, int year, String publisher, String address) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Reference (id, authors, title, year) VALUES (?, ?, ?, ?)");
        statement.setString(1, id);
        statement.setString(2, authors);
        statement.setString(3, title);
        statement.setInt(4, year);
        statement.execute();

        statement = connection.prepareStatement("INSERT INTO Book (publisher, address, id) VALUES (?, ?, ?)");
        statement.setString(1, publisher);
        statement.setString(2, address);
        statement.setString(3, id);
        statement.execute();

        statement.close();
        connection.close();
    }

}
