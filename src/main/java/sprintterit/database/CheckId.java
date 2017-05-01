package sprintterit.database;

import java.sql.SQLException;

public interface CheckId {

    boolean checkThatIdNotInUse(String id) throws SQLException;

}
