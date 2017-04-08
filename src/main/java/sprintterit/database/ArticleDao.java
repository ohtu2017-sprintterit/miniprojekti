package sprintterit.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sprintterit.models.Article;
import sprintterit.models.Authors;
import sprintterit.models.Pages;

public class ArticleDao {

    private Database database;

    public ArticleDao(Database database) {
        this.database = database;
    }

    public void addArticle(String id, String authors, String title,
            int year, String journal, int volume,
            String month, int number, int startpage, int endpage,
            String publisher, String address, String note, String key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Reference (id, authors, title, year)"
                + "VALUES (?, ?, ?, ?)");

        statement.setString(1, id);
        statement.setString(2, authors);
        statement.setString(3, title);
        statement.setInt(4, year);
        statement.execute();

        statement = connection.prepareStatement(
                "INSERT INTO Article (journal, volume, number, month, startpage, "
                + "endpage, publisher, address, note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, journal);
        statement.setInt(2, volume);
        statement.setInt(3, number);
        statement.setString(4, month);
        statement.setInt(5, startpage);
        statement.setInt(6, endpage);
        statement.setString(7, publisher);
        statement.setString(8, address);
        statement.setString(9, note);
        statement.setString(10, key);
        statement.setString(11, id);
        statement.execute();

        statement.close();
        connection.close();
    }

    public Article findOne(String id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Reference r INNER JOIN Article a "
                + "ON r.id = a.id WHERE r.id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
        
        if (!rs.isBeforeFirst()) {
            return null;
        }
        Authors authors = new Authors(rs.getString("authors"));
        String title = rs.getString("title");
        int year = rs.getInt("year");
        String journal = rs.getString("journal");
        int volume = rs.getInt("volume");
        int number = rs.getInt("number");
        String month = rs.getString("month");
        Pages pages = new Pages(rs.getInt("startpage"), rs.getInt("endpage"));
        String publisher = rs.getString("publisher");
        String address = rs.getString("address");
        String note = rs.getString("note");
        String key = rs.getString("key");
        Article article = new Article(id, authors, title, journal, 
                volume, number, month, pages, year, publisher, address, note, key);
        
        statement.close();
        connection.close();
        return article;
    }
}
