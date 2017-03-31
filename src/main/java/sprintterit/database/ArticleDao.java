package sprintterit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleDao {

    private Database database;

    public ArticleDao(Database database) {
        this.database = database;
    }

    public void addArticle(String id, String authors, String title, int year, String journal, int volume, int number, String pages, String publisher, String address) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO Reference (id, authors, title, year) VALUES (?, ?, ?, ?)");
        statement.setString(1, id);
        statement.setString(2, authors);
        statement.setString(3, title);
        statement.setInt(4, year);
        statement.execute();

        statement = connection.prepareStatement("INSERT INTO Article (journal, volume, number, pages, publisher, address, id) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, journal);
        statement.setInt(2, volume);
        statement.setInt(3, number);
        statement.setString(4, pages);
        statement.setString(5, publisher);
        statement.setString(6, address);
        statement.setString(7, id);
        statement.execute();

        statement.close();
        connection.close();
    }

}
