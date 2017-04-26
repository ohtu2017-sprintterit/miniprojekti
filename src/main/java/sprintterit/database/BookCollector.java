package sprintterit.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import sprintterit.models.Authors;
import sprintterit.models.Book;

public class BookCollector implements Collector<Book> {

    @Override
    public Book collect(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String tags = rs.getString("tags");
        Authors authors = new Authors(rs.getString("authors"));
        String title = rs.getString("title");
        Integer year = SQLInteger.get(rs, "year");
        String publisher = rs.getString("publisher");
        String address = rs.getString("address");
        Integer volume = SQLInteger.get(rs, "volume");
        String series = rs.getString("series");
        String edition = rs.getString("edition");
        String month = rs.getString("month");
        String note = rs.getString("note");
        String key = rs.getString("key");

        return new Book(id, tags, authors, title, year, publisher, address, volume, series, edition, month, note, key);
    }

}
