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
        InproceedingDao inproceedingDao = new InproceedingDao(database);
        List<Reference> references = new ArrayList<>();

        references.addAll(articleDao.findAll());
        references.addAll(bookDao.findAll());
        references.addAll(inproceedingDao.findAll());

        Collections.sort(references, Comparator.comparing(s -> s.getId().toLowerCase()));

        return references;
    }

    public List<Reference> findAll(String[] articles, String[] books, String[] inproceedings) throws SQLException {
        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        InproceedingDao inproceedingDao = new InproceedingDao(database);
        List<Reference> references = new ArrayList<>();

        if (articles != null) {
            for (String i : articles) {
                references.add(articleDao.findOne(i));
            }
        }
        if (books != null) {
            for (String i : books) {
                references.add(bookDao.findOne(i));
            }
        }
        if (inproceedings != null) {
            for (String i : inproceedings) {
                references.add(inproceedingDao.findOne(i));
            }
        }
        
        Collections.sort(references, Comparator.comparing(s -> s.getId().toLowerCase()));

        return references;
    }

}
