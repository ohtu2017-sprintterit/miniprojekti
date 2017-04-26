package sprintterit.controllers;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import sprintterit.models.Pages;

public class InputValidatorTest {

    Map<String, String> map;

    @Before
    public void createParameters() {
        map = new HashMap<>();
        map.put("id", "ohtu");
        map.put("authors", "Matti");
        map.put("title", "Ohjelmistotuotanto");
        map.put("year", "2017");
        map.put("volume", "7");
        map.put("startpage", "1");
        map.put("endpage", "100");
        map.put("empty", "");
    }

    @Test
    public void shouldReturnCorrectString() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertEquals("ohtu", input.getString("id", "", false));
        assertEquals("Matti", input.getString("authors", "", false));
        assertEquals("Ohjelmistotuotanto", input.getString("title", "", false));
    }

    @Test
    public void shouldReturnCorrectInteger() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertEquals(Integer.valueOf(2017), input.getInteger("year", "", false));
        assertEquals(Integer.valueOf(7), input.getInteger("volume", "", false));
    }

    @Test
    public void shouldReturnCorrectPageNumbers() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertEquals(new Pages(1, 100), input.getPages("startpage", "", "endpage", ""));
    }

    @Test
    public void shouldReturnEmptyStrings() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        // This is how we do it, but it's not the only way
        assertEquals("", input.getString("empty", "", false));
    }

    @Test
    public void shouldReturnNullForMissingParameter() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertNull(input.getString("missing string", "", false));
        assertNull(input.getInteger("missing integer", "", false));
    }

    @Test
    public void shouldReturnNullForInvalidInteger() {
        map.put("year", "Around 1967");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertNull(input.getInteger("year", "", false));
    }

    @Test
    public void shouldReturnNullForInvalidPageNumbers() {
        map.put("startpage", "page 1");
        map.put("endpage", "page 2");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertNull(input.getPages("startpage", "", "endpage", ""));
    }

    @Test
    public void shouldBeOkForValidInput() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        assertEquals("ohtu", input.getString("id", "", false));
        assertEquals("Matti", input.getString("authors", "", false));
        assertEquals("Ohjelmistotuotanto", input.getString("title", "", false));
        assertEquals(Integer.valueOf(2017), input.getInteger("year", "", false));
        assertEquals(Integer.valueOf(7), input.getInteger("volume", "", false));
        assertEquals("", input.getString("empty", "", false));
        assertTrue(input.isOk());
    }

    @Test
    public void shouldNotBeOkForInvalidInput() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getString("missing", "", true);

        assertFalse(input.isOk());
    }

    @Test
    public void shouldAllowOptionalParametersToBeMissing() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getString("missing but optional string", "", false);
        input.getInteger("missing but optional integer", "", false);

        assertTrue(input.isOk());
    }

    @Test
    public void shouldReportRequiredParametersWhichAreMissing() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getString("missing string", "S", true);
        input.getInteger("missing integer", "I", true);

        assertEquals("S is required", input.getErrors().get("missing string"));
        assertEquals("I is required", input.getErrors().get("missing integer"));
    }

    @Test
    public void shouldReportRequiredParametersWhichAreEmpty() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getString("empty", "E", true);

        assertEquals("E is required", input.getErrors().get("empty"));
    }

    @Test
    public void shouldReportInvalidIntegers() {
        map.put("year", "Around 1967");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getInteger("year", "Year", false);

        assertEquals("Year should be an integer", input.getErrors().get("year"));
    }

    @Test
    public void shouldReportNonIntegerPageNumber() {
        map.put("startpage", "page 1");
        map.put("endpage", "page 2");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getPages("startpage", "Startpage", "endpage", "Endpage");

        assertEquals("Startpage should be an integer", input.getErrors().get("startpage"));
        assertEquals("Endpage should be an integer", input.getErrors().get("endpage"));
    }

    @Test
    public void shouldReportStartpageWithoutEndpage() {
        map.put("startpage", "1");
        map.put("endpage", "");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getPages("startpage", "Startpage", "endpage", "Endpage");

        assertEquals("Startpage given but Endpage is missing", input.getErrors().get("endpage"));
    }

    @Test
    public void shouldReportEndpageWithoutStartpage() {
        map.put("startpage", "");
        map.put("endpage", "11");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getPages("startpage", "Startpage", "endpage", "Endpage");

        assertEquals("Endpage given but Startpage is missing", input.getErrors().get("startpage"));
    }

    @Test
    public void shouldReportStartpageLessThanOne() {
        map.put("startpage", "0");
        map.put("endpage", "0");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getPages("startpage", "Startpage", "endpage", "Endpage");

        assertEquals("Startpage is less than 1", input.getErrors().get("startpage"));
    }

    @Test
    public void shouldReportEndpageLessThanStartpage() {
        map.put("startpage", "11");
        map.put("endpage", "10");

        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getPages("startpage", "Startpage", "endpage", "Endpage");

        assertEquals("Endpage is less than Startpage", input.getErrors().get("endpage"));
    }

//    To be continued...
//    
//    @Test
//    public void shouldReportTrickyPageNumberCombination() {
//        // This combination produced a server error in exploratory testing
//        map.put("startpage", "non-integer");
//        map.put("endpage", "10");
//
//        InputStub stub = new InputStub(map);
//        InputValidator input = new InputValidator(stub);
//
//        input.getPages("startpage", "Startpage", "endpage", "Endpage");
//
//        assertEquals("Startpage should be an integer", input.getErrors().get("startpage"));
//    }
//
//    @Test
//    public void shouldReportTrickyPageNumberCombination2() {
//        // This combination produced a server error in exploratory testing
//        map.put("startpage", "10");
//        map.put("endpage", "non-integer");
//
//        InputStub stub = new InputStub(map);
//        InputValidator input = new InputValidator(stub);
//
//        input.getPages("startpage", "Startpage", "endpage", "Endpage");
//
//        assertEquals("Startpage should be an integer", input.getErrors().get("startpage"));
//    }

    @Test
    public void shouldSaveResultsOfAllQueries() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.getString("title", "", false);
        input.getInteger("year", "", false);

        assertEquals(2, input.getParameters().size());
        assertEquals("Ohjelmistotuotanto", input.getParameters().get("title"));
        assertEquals("2017", input.getParameters().get("year"));
    }

    @Test
    public void shouldBeAbleToAddErrors() {
        InputStub stub = new InputStub(map);
        InputValidator input = new InputValidator(stub);

        input.addError("name", "Error message");

        assertEquals("Error message", input.getErrors().get("name"));
    }

}
