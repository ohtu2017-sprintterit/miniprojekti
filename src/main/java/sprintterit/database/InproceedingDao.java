package sprintterit.database;

import sprintterit.models.Authors;
import sprintterit.models.Inproceeding;
import sprintterit.models.Pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InproceedingDao {

    private Database database;

    public InproceedingDao(Database database) {
        this.database = database;
    }

    public void addInproceeding(String id, String authors, String title,
            int year, String booktitle, String editor, int volume, String series,
            String month, int startpage, int endpage, String organization,
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
                "INSERT INTO Inproceedings (booktitle, startpage, endpage, publisher, "
                + "address, editor, volume, series, month, organization, "
                + "note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        statement.setString(1, booktitle);
        statement.setInt(2, startpage);
        statement.setInt(3, endpage);
        statement.setString(4, publisher);
        statement.setString(5, address);
        statement.setString(6, editor);
        statement.setInt(7, volume);
        statement.setString(8, series);
        statement.setString(9, month);
        statement.setString(10, organization);
        statement.setString(11, note);
        statement.setString(12, key);
        statement.setString(13, id);
        
        statement.execute();

        statement.close();
        connection.close();
    }

    public Inproceeding findOne(String id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Reference r INNER JOIN Inproceedings i "
                        + "ON r.id = i.id WHERE r.id = ?");
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        if (!rs.isBeforeFirst()) {
            return null;
        }

        return buildInproceeding(rs);
    }

    public List<Inproceeding> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Reference r INNER JOIN Inproceedings i "
                        + "ON r.id = i.id");
        ResultSet rs = statement.executeQuery();
        List<Inproceeding> inproceedings = new ArrayList<>();

        while (rs.next()) {
            inproceedings.add(buildInproceeding(rs));
        }

        return inproceedings;
    }

    private Inproceeding buildInproceeding(ResultSet rs) throws SQLException {
        return new Inproceeding(
                rs.getString("id"),
                new Authors(rs.getString("authors")),
                rs.getString("title"),
                rs.getString("booktitle"),
                new Pages(rs.getInt("startpage"), rs.getInt("endpage")),
                rs.getInt("year"),
                rs.getString("editor"),
                rs.getString("series"),
                rs.getString("month"),
                rs.getString("organization"),
                rs.getString("publisher"),
                rs.getString("address"),
                rs.getString("note"),
                rs.getString("key")
        );
    }
}
