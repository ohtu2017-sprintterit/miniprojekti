package sprintterit.database;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BibtexKeyGenTest {

    List<String> idList;
    CheckId idCheck;

    @Before
    public void setUsedIds() {
        idList = new ArrayList<>();
        idList.add("Fowler01");
        idList.add("Fowler02");
        idList.add("Fowler02v2");
        idList.add("Fowler03");
        idList.add("Fowler03v2");
        idList.add("Fowler03v3");
        idCheck = (id) -> {
            return !idList.contains(id);
        };
    }

    @Test
    public void testUsedIds() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("Fowler01v2", keyGen.bibtexKey("Fowler, Martin", 2001));
        assertEquals("Fowler02v3", keyGen.bibtexKey("Fowler, Martin", 2002));
        assertEquals("Fowler03v4", keyGen.bibtexKey("Fowler, Martin", 2003));
    }

    @Test
    public void testOneAuthor() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("Smith71", keyGen.bibtexKey("Smith, James", 1971));
        assertEquals("Smith92", keyGen.bibtexKey("Smith, James", 1992));
        assertEquals("Doe03", keyGen.bibtexKey("Doe, John", 2003));
        assertEquals("Doe14", keyGen.bibtexKey("Doe, John", 2014));
    }

    @Test
    public void testTwoAuthors() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("DoeSmith12", keyGen.bibtexKey("Doe, John\nSmith, James", 2012));
    }

    @Test
    public void testThreeAuthors() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("DoeSmithWest13", keyGen.bibtexKey("Doe, John\nSmith, James\nWest, Mike", 2013));
    }

    @Test
    public void testRealAuthors() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("Martin08", keyGen.bibtexKey("Martin, Robert C", 2008));
        assertEquals("SchwaberBeedle02", keyGen.bibtexKey("Schwaber, Ken\nBeedle, Mike", 2002));
        assertEquals("VihavainenLuukkainenKurhila13", keyGen.bibtexKey("Vihavainen, A.\nLuukkainen, M.\nKurhila, J.", 2013));
    }

    @Test
    public void testAuthorWithMultipleMiddleNames() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("Codd70", keyGen.bibtexKey("Codd, Edgar F.", 1970));
    }

    @Test
    public void testNameInAnotherOrder() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("KenSchwaber02", keyGen.bibtexKey("Ken Schwaber", 2002));
        assertEquals("RobertC.Martin08", keyGen.bibtexKey("Robert C. Martin", 2008));
    }

    @Test
    public void testYearWithOnlyOneDigit() throws Exception {
        BibtexKeyGen keyGen = new BibtexKeyGen(idCheck);

        assertEquals("Meikalainen0", keyGen.bibtexKey("Meikalainen, Matti", 0));
    }

}
