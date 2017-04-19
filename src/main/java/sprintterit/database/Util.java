package sprintterit.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Util {

    public static Integer getInteger(ResultSet rs, String columnLabel) throws SQLException {
        Integer value = rs.getInt(columnLabel);
        if (rs.wasNull()) {
            return null;
        }

        return value;
    }

}
