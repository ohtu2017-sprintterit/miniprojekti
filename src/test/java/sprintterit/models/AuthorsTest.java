package sprintterit.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class AuthorsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void varargsConstuctorWorksCorrectly() {
        Authors authors = new Authors("Allan Collins", "John Seely Brown", "Ann Holum");
        assertEquals(3, authors.getList().size());
        assertEquals("Allan Collins", authors.getList().get(0));
        assertEquals("John Seely Brown", authors.getList().get(1));
        assertEquals("Ann Holum", authors.getList().get(2));
    }

    @Test
    public void listConstructorWorksCorrectly() {
        List<String> list = Arrays.asList("Allan Collins", "John Seely Brown", "Ann Holum");
        Authors authors = new Authors(list);
        assertEquals(3, authors.getList().size());
        assertEquals("Allan Collins", authors.getList().get(0));
        assertEquals("John Seely Brown", authors.getList().get(1));
        assertEquals("Ann Holum", authors.getList().get(2));
    }

    @Test
    public void varargsConstructorAcceptsEmptyArray() {
        Authors authors = new Authors();
        assertEquals(true, authors.getList().isEmpty());
    }

    @Test
    public void listConstructorAcceptsEmptyList() {
        List<String> list = new ArrayList<>();
        Authors authors = new Authors(list);
        assertEquals(true, authors.getList().isEmpty());
    }

    @Test
    public void varargsConstructorThrowsExceptionWhenArrayIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("authors must not be null");
        String[] array = null;
        Authors authors = new Authors(array);
    }

    @Test
    public void listConstructorThrowsExceptionWhenListIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("authors must not be null");
        List<String> list = null;
        Authors authors = new Authors(list);
    }

    @Test
    public void toStringWorksCorrectly() {
        Authors authors = new Authors("Allan Collins", "John Seely Brown", "Ann Holum");
        assertEquals("Allan Collins and John Seely Brown and Ann Holum", authors.toString());
    }

    @Test
    public void equalsReturnsTrueForEqualObjects() {
        Authors a = new Authors("Bruhn, Russel E.", "Burton, Philip J.");
        Authors b = new Authors("Bruhn, Russel E.", "Burton, Philip J.");
        assertEquals(a, b);
    }

    @Test
    public void equalsReturnsFalseForNonEqualObjects() {
        Authors a = new Authors("Bruhn, Russel E.", "Burton, Philip J.");
        Authors b = new Authors("Bruhn, Russel E.", "Burton, Philip");
        Authors c = new Authors("Bruhn, Russel E.");
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    public void equalsReturnsFalseForObjectOfWrongType() {
        Authors a = new Authors("Bruhn, Russel E.", "Burton, Philip J.");
        assertNotEquals(a, a.toString());
    }

}
