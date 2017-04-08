package sprintterit.bibtexgen;

import sprintterit.models.Reference;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

public class BibtexGenerator {
    private HashMap<String, String> specialCharacters;

    public BibtexGenerator() {
        specialCharacters = new HashMap<>();
        specialCharacters.put("ä", "{\\\"a}");
        specialCharacters.put("ö", "{\\\"o}");
        specialCharacters.put("å", "{\\aa}");
        specialCharacters.put("Ä", "{\\\"A}");
        specialCharacters.put("Ö", "{\\\"O}");
        specialCharacters.put("Å", "{\\AA}");
    }

    public String generateBibtexFile(List<Reference> references) {
        String file = references.stream().map(ref -> ref.toString()).collect(Collectors.joining("\n"));
        specialCharacters.entrySet().forEach(e -> file.replace(e.getKey(), e.getValue()));

        return file;
    }
}
