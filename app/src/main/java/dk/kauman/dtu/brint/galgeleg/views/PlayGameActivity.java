package dk.kauman.dtu.brint.galgeleg.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dk.kauman.dtu.brint.galgeleg.R;
import dk.kauman.dtu.brint.galgeleg.controllers.GameController;
import dk.kauman.dtu.brint.galgeleg.models.Game;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotALetter;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessNotLongEnoughException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.GuessTooLongException;
import dk.kauman.dtu.brint.galgeleg.models.exceptions.LetterAlreadyGuessedException;
import dk.kauman.dtu.brint.galgeleg.models.wordproviders.WordProvider;
import dk.kauman.dtu.brint.galgeleg.models.wordproviders.DR;
import dk.kauman.dtu.brint.patterns.observer.Observer;
import dk.kauman.dtu.brint.patterns.observer.Subject;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    public static final String NUMBER_OF_GUESSES_IDENTIFIER = "numberOfGuesses";
    public static final String CORRECT_WORD_IDENTIFIER = "correctWord";

    private ImageView img;
    private TextView wordView;
    private TextView guessLetterView;
    private Button guessButton;
    private GameController controller;

    private int requestCode = (new Random()).nextInt(16);

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

        ProgressDialog dialog = ProgressDialog.show(this, "", "Indlæser spil...");

        final DR wordProvider = new DR();
        wordProvider.addObserver(this);

        bgThread.execute(() -> {
            wordProvider.setupWords();
            // this.controller.createGame(wordProvider);
            // this.controller.getGame().addObserver(this);

            mainThread.post(() -> {
                dialog.cancel();
                // this.wordView.setText(this.controller.getGame().getVisibleWord());
            });
        });
    }

    @Override
    public void onClick(View v) {
        if (v == this.guessButton) {
            String guessedLetter = guessLetterView.getText().toString();

            try {

                if (!this.controller.makeGuess(guessedLetter)) {
                    this.img.setImageDrawable(this.images.get(this.controller.getGame().getNumberOfIncorrectUsedLetters()));
                }

            } catch (LetterAlreadyGuessedException e) {
                Toast
                    .makeText(this, "Du har allerede prøvet at gætte på dette bogstav", Toast.LENGTH_LONG)
                    .show();
            } catch (GuessTooLongException | GuessNotLongEnoughException | GuessNotALetter e) {
                Toast
                    .makeText(this, "Det indtastede er ikke gyldigt. Indtast venligst 1 bogstav.", Toast.LENGTH_LONG)
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

            if (game != this.controller.getGame())
                return;

            this.wordView.setText(game.getVisibleWord());

            if (game.isWon()) {
                this.controller.saveGameResult(this);

                Intent intent = new Intent(this, GameWonActivity.class);
                intent.putExtra(NUMBER_OF_GUESSES_IDENTIFIER, this.controller.getGame().getNumberOfUsedLetters());
                startActivity(intent);
                finish();
            }

            if (game.isLost()) {
                Intent intent = new Intent(this, GameLostActivity.class);
                intent.putExtra(CORRECT_WORD_IDENTIFIER, this.controller.getGame().getWord());
                startActivity(intent);
                finish();
            }
        }

        if (subject instanceof WordProvider) {
            if (((WordProvider) subject).getWords().size() <= 0)
                return;

            Intent i = new Intent(this, SelectWordActivity.class);
            i.putStringArrayListExtra(SelectWordActivity.WORD_LIST_IDENTIFIER, ((WordProvider) subject).getWords());
            startActivityForResult(i, this.requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            String word = data.getStringExtra(SelectWordActivity.WORD_IDENTIFIER);
            this.controller.createGame(word).addObserver(this);
            this.wordView.setText(this.controller.getGame().getVisibleWord());
        }
    }
}