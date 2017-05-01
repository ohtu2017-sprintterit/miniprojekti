package sprintterit.controllers;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores request parameters as strings.
 *
 * Request parameters are stored as strings because they have to be validated.
 * So, if they are invalid, they may not be convertible to their native types.
 * For instance, if the user enters a string into a field which expects an
 * integer.
 *
 * This is a wrapper around a hash map with string keys and values.
 */
public class Parameters {

    private final Map<String, String> parameters;

    public Parameters() {
        this.parameters = new HashMap<>();
    }

    public String get(String name) {
        return parameters.get(name);
    }

    public void put(String name, String value) {
        if (value == null) {
            putNull(name);
            return;
        }

        parameters.put(name, value);
    }

    public void put(String name, Integer value) {
        if (value == null) {
            putNull(name);
            return;
        }

        parameters.put(name, value.toString());
    }

    private void putNull(String name) {
        parameters.put(name, "");
    }

    public int size() {
        return parameters.size();
    }

    public Map<String, String> getMap() {
        return parameters;
    }

}
