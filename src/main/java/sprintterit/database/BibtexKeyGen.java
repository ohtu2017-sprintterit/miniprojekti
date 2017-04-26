package sprintterit.database;

import java.sql.SQLException;

public class BibtexKeyGen {

    private Database database;

    public BibtexKeyGen(Database db) {
        database = db;
    }

    public String bibtexKey(String authors, int year) throws SQLException {
        String key = "";
        String[] a = authors.split("\\r?\\n");
        for (int i = 0; i < 3 && i < a.length; i++) {
            int k = a[i].indexOf(",");
            if (k == -1) {
                continue;
            }
            key += a[i].substring(0, k);
        }
        if (key.isEmpty()) {
            key += a[0];
        }
        String y = Integer.toString(year);
        y = y.substring(y.length() - 2, y.length());
        key += y;
        // Looks horrible, here for now
        for (int i = 2; i < 10; i++) {
            if (checkThatIdNotInUse(key)) {
                break;
            }
            if (i > 2) {
                key = key.substring(0, key.length() - 2);
            }
            key += "v" + i;
        }
        return key;
    }

    private boolean checkThatIdNotInUse(String id) throws SQLException {
        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        InproceedingDao inproceedingDao = new InproceedingDao(database);
        return (articleDao.findOne(id) == null && bookDao.findOne(id) == null && inproceedingDao.findOne(id) == null);
    }
}
