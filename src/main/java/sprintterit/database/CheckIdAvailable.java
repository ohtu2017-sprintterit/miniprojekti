package sprintterit.database;

import java.sql.SQLException;

public interface CheckIdAvailable {

    boolean checkThatIdNotInUse(String id) throws SQLException;

}
