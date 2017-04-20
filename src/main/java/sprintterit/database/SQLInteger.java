package sprintterit.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Reading and writing integers, some of which may be null (empty fields on a
 * form). These should be portable across different database systems, including
 * differences among PostgreSQL and SQLite (PostgreSQL seems more strict).
 */
public class SQLInteger {

    public static Integer get(ResultSet rs, String columnLabel) throws SQLException {
        Integer x = rs.getInt(columnLabel);
        if (rs.wasNull()) {
            return null;
        }

        return x;
    }

    public static void set(PreparedStatement stmt, int columnIndex, Integer x) throws SQLException {
        if (x == null) {
            stmt.setNull(columnIndex, java.sql.Types.INTEGER);
            return;
        }

        stmt.setInt(columnIndex, x);
    }

}
