package sprintterit.database;

import java.sql.SQLException;
import java.util.List;
import sprintterit.models.Article;
import sprintterit.models.Pages;

public class ArticleDao {

    private final Database database;
    private final QueryRunner<Article> query;

    public ArticleDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new ArticleCollector());
    }

    public void addArticle(String id, String authors, String title,
            Integer year, String journal, Integer volume,
            String month, Integer number, Pages pages,
            String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "INSERT INTO Reference (id, authors, title, year)"
                + "VALUES (?, ?, ?, ?)",
                id, authors, title, year);
        query.insert(
                "INSERT INTO Article (journal, volume, number, month, startpage, "
                + "endpage, publisher, address, note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                journal, volume, number, month,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, note, key, id);
    }

    public Article findOne(String id) throws SQLException {
        return query.queryObject(
                "SELECT * FROM Reference r INNER JOIN Article a "
                + "ON r.id = a.id WHERE r.id = ?", id);
    }

    public List<Article> findAll() throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Article a ON r.id = a.id");
    }

    public void editArticle(String id, String authors, String title, Integer year, String journal, Integer volume, String month, Integer number, Pages pages, String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, title = ?, year = ? WHERE id = ?",
                authors, title, year, id);
        query.insert(
                "UPDATE Article SET journal = ?, volume = ?, number = ?, month = ?, startpage = ?, endpage = ?, publisher = ?, address = ?, note = ?, key = ? WHERE id = ?",
                journal, volume, number, month,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, note, key, id);
    }

}
