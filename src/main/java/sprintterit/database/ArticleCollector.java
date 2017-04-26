package sprintterit.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import sprintterit.models.Article;
import sprintterit.models.Authors;
import sprintterit.models.Pages;

public class ArticleCollector implements Collector<Article> {

    @Override
    public Article collect(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String tags = rs.getString("tags");
        Authors authors = new Authors(rs.getString("authors"));
        String title = rs.getString("title");
        Integer year = SQLInteger.get(rs, "year");
        String journal = rs.getString("journal");
        Integer volume = SQLInteger.get(rs, "volume");
        Integer number = SQLInteger.get(rs, "number");
        String month = rs.getString("month");
        Pages pages = Pages.construct(SQLInteger.get(rs, "startpage"), SQLInteger.get(rs, "endpage"));
        String publisher = rs.getString("publisher");
        String address = rs.getString("address");
        String note = rs.getString("note");
        String key = rs.getString("key");

        return new Article(id, tags, authors, title, journal, volume, number, month,
                pages, year, publisher, address, note, key);
    }

}
