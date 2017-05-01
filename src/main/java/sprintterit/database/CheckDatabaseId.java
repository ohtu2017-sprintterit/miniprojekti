package sprintterit.database;

import java.sql.SQLException;

public class CheckDatabaseId implements CheckIdAvailable {

    private final Database database;

    public CheckDatabaseId(Database database) {
        this.database = database;
    }

    @Override
    public boolean checkThatIdNotInUse(String id) throws SQLException {
        ArticleDao articleDao = new ArticleDao(database);
        BookDao bookDao = new BookDao(database);
        InproceedingDao inproceedingDao = new InproceedingDao(database);
        return (articleDao.findOne(id) == null && bookDao.findOne(id) == null && inproceedingDao.findOne(id) == null);
    }

}
