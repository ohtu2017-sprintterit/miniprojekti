package sprintterit.database;

import sprintterit.models.Reference;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ReferenceDao {
    private Database database;

    public ReferenceDao(Database db) {
        database = db;
    }

    public List<Reference> findAll() throws SQLException {
        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        List<Reference> references = new ArrayList<>();

        references.addAll(articleDao.findAll());
        references.addAll(bookDao.findAll());

        Collections.sort(references, Comparator.comparing(Reference::getId));

        return references;
    }
}
