package sprintterit.database;

import java.sql.SQLException;
import java.util.List;
import sprintterit.models.Article;
import sprintterit.models.Pages;

public class ArticleDao implements DaoWithDelete {

    private final Database database;
    private final QueryRunner<Article> query;
    private final BibtexKeyGen keygen;

    public ArticleDao(Database database) {
        this.database = database;
        this.query = new QueryRunner<>(database, new ArticleCollector());
        this.keygen = new BibtexKeyGen(new CheckDatabaseId(database));
    }

    public void addArticle(String tags, String authors, String title,
            Integer year, String journal, Integer volume,
            String month, Integer number, Pages pages,
            String publisher, String address, String note, String key) throws SQLException {
        String bibKey = keygen.bibtexKey(authors, year);
        query.insert(
                "INSERT INTO Reference (id, tags, authors, title, year)"
                + "VALUES (?, ?, ?, ?, ?)",
                bibKey, tags, authors, title, year);
        query.insert(
                "INSERT INTO Article (journal, volume, number, month, startpage, "
                + "endpage, publisher, address, note, key, id)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                journal, volume, number, month,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, note, key, bibKey);
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

    public void editArticle(String id, String tags, String authors, String title, Integer year, String journal, Integer volume, String month, Integer number, Pages pages, String publisher, String address, String note, String key) throws SQLException {
        query.insert(
                "UPDATE Reference SET authors = ?, tags = ?, title = ?, year = ? WHERE id = ?",
                authors, tags, title, year, id);
        query.insert(
                "UPDATE Article SET journal = ?, volume = ?, number = ?, month = ?, startpage = ?, endpage = ?, publisher = ?, address = ?, note = ?, key = ? WHERE id = ?",
                journal, volume, number, month,
                pages == null ? null : pages.getBegin(),
                pages == null ? null : pages.getEnd(),
                publisher, address, note, key, id);
    }

    @Override
    public void delete(String id) throws SQLException {
        if (findOne(id) == null) {
            return;
        }

        query.insert("DELETE FROM Article WHERE id = ?", id);
        query.insert("DELETE FROM Reference WHERE id = ?", id);
    }

    public List<Article> findTag(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Article a ON r.id = a.id WHERE lower(r.tags) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Article> findAuthor(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Article a ON r.id = a.id WHERE lower(r.authors) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Article> findTitle(String word) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Article a ON r.id = a.id WHERE lower(r.title) LIKE '%" + word.toLowerCase() + "%'");
    }

    public List<Article> findYear(int year) throws SQLException {
        return query.queryList(
                "SELECT * FROM Reference r INNER JOIN Article a ON r.id = a.id WHERE r.year = ?", year);
    }

}
