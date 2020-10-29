package dk.kauman.dtu.brint.galgeleg.models;

import java.util.ArrayList;

import dk.kauman.dtu.brint.galgeleg.models.enums.GameStatus;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.patterns.observer.Subject;

public class Game extends Subject {

    private final String word;
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    public Game(String word) {
        this.word = word;
    }

    public boolean guessLetter(String letter) throws GuessTooLongException, GuessNotLongEnoughException, LetterAlreadyGuessedException {
        if (letter.length() > 1)
            throw new GuessTooLongException();

        if (letter.length() < 1)
            throw new GuessNotLongEnoughException();

        if (usedLetters.contains(letter))
            throw new LetterAlreadyGuessedException();

        if (isWon() || isLost())
            return false;

        usedLetters.add(letter);

        notifyAllObservers();
        updateStatus();

        return word.contains(letter);
    }

    private void updateStatus() {
        boolean ongoing = false;

        for (Character c : getVisibleWord().toCharArray()) {
            if (c.equals("*".charAt(0)))
                ongoing = true;
        }

        if (!ongoing) {
            setStatus(GameStatus.WON);
            return;
        }

        if (usedLetters.size() >= this.word.length()) {
            setStatus(GameStatus.LOST);
            return;
        }
    }

    public void setStatus(GameStatus status) {
        this.status = status;
        notifyAllObservers();
    }

    public GameStatus getStatus() {
        return status;
    }

    public String getVisibleWord() {
        String retValue = "";

        for (Character c : this.word.toCharArray()) {
            if (this.usedLetters.contains(c.toString())) {
                retValue = retValue + c.toString();
            } else {
                retValue = retValue + "*";
            }
        }

        return retValue;
    }

    public boolean isWon() {
        return this.getStatus().equals(GameStatus.WON);
    }

    public boolean isLost() {
        return this.getStatus().equals(GameStatus.LOST);
    }

    public int getNumberOfUsedLetters() {
        return this.usedLetters.size();
    }
}
