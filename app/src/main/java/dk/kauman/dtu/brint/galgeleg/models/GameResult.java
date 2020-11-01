package dk.kauman.dtu.brint.galgeleg.models;

import com.google.gson.Gson;

import java.util.Date;

public class GameResult {

    private Date date;
    private int numberOfGuesses;
    private String word = "";

    public static GameResult fromGame(Game game) {
        GameResult result = new GameResult();

        result.date = new Date();
        result.numberOfGuesses = game.getNumberOfUsedLetters();
        result.word = game.getWord();

        return result;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static GameResult fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GameResult.class);
    }

    public Date getDate() {
        return date;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }

    public String getWord() {
        return word;
    }

    public int getScore() {
        int val = this.word.length() - this.numberOfGuesses;

        if (val < 0)
            val = 0;

        return val;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "date=" + date +
                ", numberOfGuesses=" + numberOfGuesses +
                ", word='" + word + '\'' +
                '}';
    }
}
