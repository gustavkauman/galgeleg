package dk.kauman.dtu.brint.galgeleg.models;

import dk.kauman.dtu.brint.galgeleg.models.enums.GameStatus;

public class Game {

    private final String word;
    private GameStatus status = GameStatus.IN_PROGRESS;

    public Game(String word) {
        this.word = word;
    }

    public GameStatus getStatus() {
        return status;
    }

    public boolean isWon() {
        return this.status.equals(GameStatus.WON);
    }

    public boolean isLost() {
        return this.status.equals(GameStatus.LOST);
    }
}
