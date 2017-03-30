package sprintterit.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Authors {

    private final List<String> authors;

    public Authors(String... authors) {
        authors = Objects.requireNonNull(authors, "authors must not be null");
        this.authors = new ArrayList<>(Arrays.asList(authors));
    }

    public Authors(List<String> authors) {
        this.authors = Objects.requireNonNull(authors, "authors must not be null");
    }

    public List<String> getList() {
        return authors;
    }

    @Override
    public String toString() {
        return String.join(" and ", authors);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Authors)) {
            return false;
        }

        Authors a = (Authors) obj;
        return authors.equals(a.getList());
    }

}
