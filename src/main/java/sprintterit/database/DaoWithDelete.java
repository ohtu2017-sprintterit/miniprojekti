package sprintterit.database;

import java.sql.SQLException;

/**
 * Deleting a reference is a separate interface because not all DAOs have it.
 *
 * ReferenceDao does not have a delete action, the other DAO classes can delete.
 */
public interface DaoWithDelete {

    void delete(String id) throws SQLException;

}
