package sprintterit.database;

import java.sql.SQLException;
import java.text.Normalizer;

public class BibtexKeyGen {

    private final CheckId idCheck;

    public BibtexKeyGen(CheckId idCheck) {
        this.idCheck = idCheck;
    }

    public String bibtexKey(String authors, int year) throws SQLException {
        String key = "";
        authors = Normalizer.normalize(authors, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
        String[] a = authors.split("\\r?\\n");
        for (int i = 0; i < 3 && i < a.length; i++) {
            int k = a[i].indexOf(",");
            if (k == -1) {
                continue;
            }
            key += a[i].substring(0, k);
        }
        if (key.isEmpty()) {
            key += a[0].replaceAll("\\s+", "");
        }
        String y = Integer.toString(year);
        y = y.substring(Math.max(0, y.length() - 2), y.length());
        key += y;
        // Looks horrible, here for now
        for (int i = 2; i < 10; i++) {
            if (idCheck.checkThatIdNotInUse(key)) {
                break;
            }
            if (i > 2) {
                key = key.substring(0, key.length() - 2);
            }
            key += "v" + i;
        }
        return key;
    }

}
