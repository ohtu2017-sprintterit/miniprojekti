package sprintterit.bibtexgen;

import org.junit.Before;
import org.junit.Test;
import sprintterit.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BibtexGeneratorTest {
    private BibtexGenerator gen;

    @Before
    public void setUp() {
        gen = new BibtexGenerator();
    }

    @Test
    public void testBibtexGeneration() {
        List<Reference> references = new ArrayList<>();
        references.add(new Article(
                "a",
                "tag",
                new Authors("Author, Adam\nWriter, William"),
                "A whole lot of nothing",
                "Trash",
                3,
                5,
                null,
                null,
                2007,
                null,
                null,
                null,
                null
        ));
        references.add(new Book("123", "tag", new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann"),
                "C programming", 1984, "Publisher", "Scienceroad",
                6, "Programming", "edition", "Feb", "note", "key"));
        references.add(new Inproceeding("1234", "tag", new Authors("Collins, Allan\nBrown, John Seely\nHolum, Ann"),
                "title", "booktitle", new Pages(38, 46), 1999, "editor", 9,
                "series", "month", "org", "publisher", "address", "note", "key"));

        String expected = String.join(
                "\n",
                "@article{a,",
                "  author = {Author, Adam and Writer, William},",
                "  title = {A whole lot of nothing},",
                "  journal = {Trash},",
                "  year = \"2007\",",
                "  volume = \"3\",",
                "  number = \"5\"",
                "}\n",
                "@book{123,",
                "  author = {Collins, Allan and Brown, John Seely and Holum, Ann},",
                "  title = {C programming},",
                "  publisher = {Publisher},",
                "  year = \"1984\",",
                "  volume = \"6\",",
                "  series = \"Programming\",",
                "  address = \"Scienceroad\",",
                "  edition = \"edition\",",
                "  month = Feb,",
                "  note = {note},",
                "  key = {key}",
                "}\n",
                "@inproceedings{1234,",
                "  author = {Collins, Allan and Brown, John Seely and Holum, Ann},",
                "  title = {title},",
                "  booktitle = {booktitle},",
                "  year = \"1999\",",
                "  editor = {editor},",
                "  volume = \"9\",",
                "  series = {series},",
                "  pages = {38--46},",
                "  address = {address},",
                "  organization = {org},",
                "  publisher = {publisher},",
                "  month = month,",
                "  note = {note},",
                "  key = {key}",
                "}\n"
        );

        assertEquals(expected, gen.generateBibtexFile(references));
    }
}
