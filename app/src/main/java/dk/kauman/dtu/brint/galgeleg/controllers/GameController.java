package dk.kauman.dtu.brint.galgeleg.controllers;

import java.util.List;
import java.util.Random;

import dk.kauman.dtu.brint.galgeleg.models.Game;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotALetter;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.galgeleg.models.wordproviders.WordProvider;

public class GameController {

    private Game game;

    public Game createGame(WordProvider wordProvider) {
        // Get list of possible words from the word provider.
        List<String> possibleWords = wordProvider.getWords();

        if (possibleWords.isEmpty())
            throw new IllegalStateException("The word provider did not provide any words!");

        // Select a random index
        int randomIndex = (new Random()).nextInt(possibleWords.size());

        // Create new game
        this.game = new Game(possibleWords.get(randomIndex));

        return this.game;
    }

    public boolean makeGuess(String letter) throws LetterAlreadyGuessedException, GuessTooLongException, GuessNotLongEnoughException, GuessNotALetter {
        return game.guessLetter(letter);
    }

    public Game getGame() {
        return game;
    }
}
