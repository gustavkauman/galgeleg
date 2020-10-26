package dk.kauman.dtu.brint.galgeleg.controllers;

import java.util.List;
import java.util.Random;

import dk.kauman.dtu.brint.galgeleg.models.Game;
import dk.kauman.dtu.brint.galgeleg.models.interfaces.WordProvider;

public class GameController {

    private Game game;

    public Game createGame(WordProvider wordProvider) {
        // Get list of possible words from the word provider.
        List<String> possibleWords = wordProvider.getWords();

        // Select a random index
        int randomIndex = (new Random()).nextInt(possibleWords.size());

        // Create new game
        this.game = new Game(possibleWords.get(randomIndex));

        return this.game;
    }

    public Game getGame() {
        return game;
    }
}