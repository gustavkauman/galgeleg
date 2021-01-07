package dk.kauman.dtu.brint.galgeleg.models.wordproviders;

import java.util.ArrayList;
import java.util.List;

import dk.kauman.dtu.brint.patterns.observer.Subject;

public abstract class WordProvider extends Subject {

    ArrayList<String> words = new ArrayList<String>();

    public ArrayList<String> getWords() {
        return words;
    }
}
