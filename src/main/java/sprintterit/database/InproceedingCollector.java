package sprintterit.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import sprintterit.models.Authors;
import sprintterit.models.Inproceeding;
import sprintterit.models.Pages;

public class InproceedingCollector implements Collector<Inproceeding> {

    @Override
    public Inproceeding collect(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String tags = rs.getString("tags");
        Authors authors = new Authors(rs.getString("authors"));
        String title = rs.getString("title");
        String booktitle = rs.getString("booktitle");
        Pages pages = Pages.construct(SQLInteger.get(rs, "startpage"), SQLInteger.get(rs, "endpage"));
        Integer year = SQLInteger.get(rs, "year");
        String editor = rs.getString("editor");
        Integer volume = SQLInteger.get(rs, "volume");
        String series = rs.getString("series");
        String month = rs.getString("month");
        String organization = rs.getString("organization");
        String publisher = rs.getString("publisher");
        String address = rs.getString("address");
        String note = rs.getString("note");
        String key = rs.getString("key");

        return new Inproceeding(id, tags, authors, title, booktitle, pages, year, editor, volume, series, month, organization, publisher, address, note, key);
    }

}
