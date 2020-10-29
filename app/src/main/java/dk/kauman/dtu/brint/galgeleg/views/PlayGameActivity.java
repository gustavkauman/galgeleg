package dk.kauman.dtu.brint.galgeleg.views;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.kauman.dtu.brint.galgeleg.R;
import dk.kauman.dtu.brint.galgeleg.controllers.GameController;
import dk.kauman.dtu.brint.galgeleg.models.Game;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.galgeleg.models.interfaces.WordProvider;
import dk.kauman.dtu.brint.galgeleg.models.wordproviders.DR;
import dk.kauman.dtu.brint.patterns.observer.Observer;
import dk.kauman.dtu.brint.patterns.observer.Subject;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private ImageView img;
    private TextView wordView;
    private TextView guessLetterView;
    private Button guessButton;
    private GameController controller;
    private Game game;

    private HashMap<Integer, Drawable> images = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        this.img = findViewById(R.id.hangManImage);
        this.wordView = findViewById(R.id.secretWordText);
        this.guessLetterView = findViewById(R.id.nextLetterInput);
        this.guessButton = findViewById(R.id.guessButton);
        this.guessButton.setOnClickListener(this);

        this.controller = new GameController();

        Drawable image = getDrawable(R.drawable.galge);
        this.img.setImageDrawable(image);

        setUpGame();
        setUpImages();
    }

    private void setUpImages() {
        this.images.put(1, getDrawable(R.drawable.forkert1));
        this.images.put(2, getDrawable(R.drawable.forkert2));
        this.images.put(3, getDrawable(R.drawable.forkert3));
        this.images.put(4, getDrawable(R.drawable.forkert4));
        this.images.put(5, getDrawable(R.drawable.forkert5));
        this.images.put(6, getDrawable(R.drawable.forkert6));
    }

    public void setUpGame() {
        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler mainThread = new Handler(Looper.getMainLooper());

        ProgressDialog dialog = ProgressDialog.show(this, "", "Loading game...");

        bgThread.execute(() -> {
            WordProvider wordProvider = new DR();
            this.game = this.controller.createGame(wordProvider);
            this.game.addObserver(this);

            mainThread.post(() -> {
                dialog.cancel();
                this.wordView.setText(this.controller.getGame().getVisibleWord());
            });
        });
    }

    @Override
    public void onClick(View v) {
        if (v == this.guessButton) {
            String guessedLetter = guessLetterView.getText().toString();

            try {

                if (!this.controller.makeGuess(guessedLetter)) {
                    this.img.setImageDrawable(this.images.get(this.game.getNumberOfUsedLetters()));
                }

            } catch (LetterAlreadyGuessedException e) {
                Toast
                    .makeText(this, "You've already tried to guess this letter", Toast.LENGTH_LONG)
                    .show();
            } catch (GuessTooLongException | GuessNotLongEnoughException e) {
                Toast
                    .makeText(this, "The entered does not match the criteria. Pleast enter a single letter", Toast.LENGTH_LONG)
                    .show();
            }

            clearTextField();
        }
    }

    private void clearTextField() {
        this.guessLetterView.setText("");
    }

    @Override
    public void onNotify(Subject subject) {
        if (subject instanceof Game) {
            Game game = (Game) subject;

            this.wordView.setText(game.getVisibleWord());

            if (game.isWon()) {
                Toast.makeText(this, "You've won the game!", Toast.LENGTH_SHORT).show();
            }

            if (game.isLost()) {
                Toast.makeText(this, "You've lost the game :(", Toast.LENGTH_SHORT).show();
            }
        }
    }
}