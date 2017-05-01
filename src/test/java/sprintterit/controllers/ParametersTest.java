package sprintterit.controllers;

import org.junit.Test;
import static org.junit.Assert.*;

public class ParametersTest {

    @Test
    public void shouldReturnCorrectValue() {
        Parameters parameters = new Parameters();
        parameters.put("parameter 1", 2001);
        parameters.put("parameter 2", 2002);
        parameters.put("parameter 3", "ada");
        parameters.put("parameter 4", "pekka");

        assertEquals("2001", parameters.get("parameter 1"));
        assertEquals("2002", parameters.get("parameter 2"));
        assertEquals("ada", parameters.get("parameter 3"));
        assertEquals("pekka", parameters.get("parameter 4"));
        assertEquals(4, parameters.size());
        assertEquals(4, parameters.getMap().size());
    }

    @Test
    public void shouldConvertNullToEmptyString() {
        Parameters parameters = new Parameters();
        parameters.put("null string", (String) null);
        parameters.put("null integer", (Integer) null);

        assertEquals("", parameters.get("null string"));
        assertEquals("", parameters.get("null integer"));
        assertEquals(2, parameters.size());
    }

}
