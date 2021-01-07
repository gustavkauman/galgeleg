package dk.kauman.dtu.brint.galgeleg.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

import dk.kauman.dtu.brint.galgeleg.models.Game;
import dk.kauman.dtu.brint.galgeleg.models.GameResult;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotALetter;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.galgeleg.models.wordproviders.WordProvider;

public class GameController {

    private Game game;

    public Game createGame(String word) {
        return this.game = new Game(word);
    }

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
        return game.guessLetter(letter.toLowerCase());
    }

    public void saveGameResult(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Get existing results
        Gson gson = new Gson();
        ArrayList<GameResult> results = gson.fromJson(prefs.getString("games", "[]"), new TypeToken<ArrayList<GameResult>>(){}.getType());

        // Add new result
        results.add(GameResult.fromGame(this.game));

        // Update saved variables
        prefs.edit().putString("games", gson.toJson(results)).apply();
    }

    public Game getGame() {
        return game;
    }
}
