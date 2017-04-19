package sprintterit.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import sprintterit.models.Article;
import sprintterit.models.Authors;

public class ArticleCollector implements Collector<Article> {

    @Override
    public Article collect(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        Authors authors = new Authors(rs.getString("authors"));
        String title = rs.getString("title");
        Integer year = Util.getInteger(rs, "year");
        String journal = rs.getString("journal");
        Integer volume = Util.getInteger(rs, "volume");
        Integer number = Util.getInteger(rs, "number");
        String month = rs.getString("month");
        Integer startpage = Util.getInteger(rs, "startpage");
        Integer endpage = Util.getInteger(rs, "endpage");
        String publisher = rs.getString("publisher");
        String address = rs.getString("address");
        String note = rs.getString("note");
        String key = rs.getString("key");

        return new Article(id, authors, title, journal, volume, number, month,
                startpage, endpage, year, publisher, address, note, key);
    }

}
