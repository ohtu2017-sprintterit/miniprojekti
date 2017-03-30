package sprintterit.models;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class PagesTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorWorksCorrectly() {
        Pages pages = new Pages(38, 46);
        assertEquals(38, pages.getBegin());
        assertEquals(46, pages.getEnd());
    }

    @Test
    public void throwsExceptionWhenBeginWhenLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Illegal page interval 0--5");
        Pages pages = new Pages(0, 5);
    }

    @Test
    public void throwsExceptionWhenBeginGreaterThanEnd() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Illegal page interval 5--4");
        Pages pages = new Pages(5, 4);
    }

    @Test
    public void toStringWorksCorrectly() {
        Pages pages = new Pages(38, 46);
        assertEquals("38--46", pages.toString());
    }

    @Test
    public void equalsReturnsTrueForEqualObjects() {
        assertEquals(new Pages(38, 46), new Pages(38, 46));
    }

    @Test
    public void equalsReturnsFalseForNonEqualObjects() {
        assertNotEquals(new Pages(38, 46), new Pages(37, 46));
        assertNotEquals(new Pages(38, 46), new Pages(38, 47));
    }

    @Test
    public void equalsReturnsFalseForObjectOfWrongType() {
        assertNotEquals(new Pages(38, 46), "38--46");
    }

}
