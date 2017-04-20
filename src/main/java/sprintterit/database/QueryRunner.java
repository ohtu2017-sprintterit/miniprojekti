package sprintterit.database;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryRunner<T> {

    private final Database db;
    private final Collector<T> collector;

    public QueryRunner(Database db, Collector<T> collector) {
        this.db = db;
        this.collector = collector;
    }

    public List<T> queryList(String query, Object... params) throws SQLException {
        List<T> rows = new ArrayList<>();
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = prepare(connection, query, params);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rows.add(collector.collect(rs));
            }
        }

        return rows;
    }

    public T queryObject(String query, Object... params) throws SQLException {
        List<T> rows = queryList(query, params);
        if (rows.isEmpty()) {
            return null;
        }

        return rows.get(0);
    }

    public String insert(String query, Object... params) throws SQLException {
        String generatedKey = null;
        try (Connection connection = db.getConnection();
             PreparedStatement stmt = prepareInsert(connection, query, params)) {
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedKey = rs.getString(1);
                }
            }
        }

        return generatedKey;
    }

    private PreparedStatement prepare(Connection connection, String query, Object[] params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        setParameters(stmt, params);
        return stmt;
    }

    private PreparedStatement prepareInsert(Connection connection, String query, Object[] params) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setParameters(stmt, params);
        return stmt;
    }

    private void setParameters(PreparedStatement stmt, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            setParameter(stmt, i + 1, params[i]);
        }
    }

    private void setParameter(PreparedStatement stmt, int index, Object param) throws SQLException {
        if (param == null) {
            // This should work also on PostgreSQL
            // Oracle might still have a problem with this form
            ParameterMetaData pmd = stmt.getParameterMetaData();
            stmt.setNull(index, pmd.getParameterType(index));
            return;
        }

        // This might not work if param == null (setObject with untyped null)
        // On most databases this is ok, but PostgreSQL seems to prohibit this form
        stmt.setObject(index, param);
    }

}
