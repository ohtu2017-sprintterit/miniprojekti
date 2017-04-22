package sprintterit.bibtexgen;

import sprintterit.models.Reference;

import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

public class BibtexGenerator {
    private HashMap<String, String> specialCharacters;

    public BibtexGenerator() {
        specialCharacters = new HashMap<>();
        specialCharacters.put("\u00E4", "{\\\"a}");
        specialCharacters.put("\u00F6", "{\\\"o}");
        specialCharacters.put("\u00E5", "{\\aa}");
        specialCharacters.put("\u00C4", "{\\\"A}");
        specialCharacters.put("\u00D6", "{\\\"O}");
        specialCharacters.put("\u00C5", "{\\AA}");
    }

    public String generateBibtexFile(List<Reference> references) {
        String file = references.stream().map(ref -> ref.toString()).collect(Collectors.joining("\n"));
        file = specialCharacters.keySet()
                .stream()
                .reduce(file, (output, character) -> output.replace(character, specialCharacters.get(character)));

        return file;
    }
}
