package dk.kauman.dtu.brint.galgeleg.models;

import java.util.ArrayList;

import dk.kauman.dtu.brint.galgeleg.models.enums.GameStatus;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotALetter;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.patterns.observer.Subject;

public class Game extends Subject {

    public static final int ALLOWED_NUMBER_OF_GUESSES = 6;

    private final String word;
    private ArrayList<String> usedLetters = new ArrayList<String>();
    private GameStatus status = GameStatus.IN_PROGRESS;

    public Game(String word) {
        this.word = word;
        System.out.println("Word is: " + this.word);
    }

    public boolean guessLetter(String letter) throws GuessTooLongException, GuessNotLongEnoughException, LetterAlreadyGuessedException, GuessNotALetter {
        if (letter.length() > 1)
            throw new GuessTooLongException();

        if (letter.length() < 1)
            throw new GuessNotLongEnoughException();

        if (!letter.matches("[a-zæøå]")) {
            throw new GuessNotALetter();
        }

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
        Character letterIsMissingChar = '*';

        for (Character c : getVisibleWord().toCharArray()) {
            if (c.equals(letterIsMissingChar))
                ongoing = true;
        }

        if (!ongoing) {
            setStatus(GameStatus.WON);
            return;
        }

        if (getNumberOfIncorrectUsedLetters() >= ALLOWED_NUMBER_OF_GUESSES) {
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
        StringBuilder builder = new StringBuilder();

        for (Character c : this.word.toCharArray()) {
            if (this.usedLetters.contains(c.toString())) {
                builder.append(c.toString());
            } else {
                builder.append("*");
            }
        }

        return builder.toString();
    }

    public String getWord() {
        return this.word;
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

    public int getNumberOfIncorrectUsedLetters() {
        int val = 0;

        for (String s : this.usedLetters) {
            if (!this.word.contains(s)) {
                val++;
            }
        }

        return val;
    }
}
