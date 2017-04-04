package sprintterit.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }
        } catch (Throwable t) {
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("CREATE TABLE IF NOT EXISTS Reference (id varchar(50) PRIMARY KEY, authors varchar(200), title varchar(100), year integer);");
        lista.add("CREATE TABLE IF NOT EXISTS Article (journal varchar(100), volume integer, number integer, startpage integer, endpage integer, publisher varchar(100), address varchar(100), id varchar(50), FOREIGN KEY(id) REFERENCES Reference(id));");
        lista.add("CREATE TABLE IF NOT EXISTS Book (publisher varchar(100), address varchar(100), id varchar(50), FOREIGN KEY(id) REFERENCES Reference(id));");
        lista.add("CREATE TABLE IF NOT EXISTS Inproceedings (booktitle varchar(100), pages varchar(50), publisher varchar(100), address varchar(100), id varchar(50), FOREIGN KEY(id) REFERENCES Reference(id));");

        return lista;
    }
}
