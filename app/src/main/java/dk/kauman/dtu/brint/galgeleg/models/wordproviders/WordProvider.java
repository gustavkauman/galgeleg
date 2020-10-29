package dk.kauman.dtu.brint.galgeleg.models.wordproviders;

import java.util.ArrayList;
import java.util.List;

public abstract class WordProvider {

    ArrayList<String> words = new ArrayList<String>();

    public List<String> getWords() {
        return words;
    }
}
