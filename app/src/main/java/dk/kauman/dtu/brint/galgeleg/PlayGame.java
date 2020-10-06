package dk.kauman.dtu.brint.galgeleg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlayGame extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private String word;
    private TextView wordView;
    private Galgelogik logik;
    private TextView guessWordView;
    private Button guessButton;

    private int numberOfWrongLetters;

    private HashMap<Integer, Integer> images = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        this.img = findViewById(R.id.hangManImage);
        this.wordView = findViewById(R.id.secretWordText);
        this.guessWordView = findViewById(R.id.nextLetterInput);
        this.guessButton = findViewById(R.id.guessButton);
        this.logik = new Galgelogik();

        this.numberOfWrongLetters = this.logik.getAntalForkerteBogstaver();

        this.guessButton.setOnClickListener(this);

        Drawable image = getDrawable(R.drawable.galge);
        this.img.setImageDrawable(image);

        Executor bgThread = Executors.newSingleThreadExecutor();
        Handler mainThread = new Handler(Looper.getMainLooper());

        bgThread.execute(() -> {
            try {
                this.logik.hentOrdFraDr();

                mainThread.post(() -> {
                    this.word = this.logik.getSynligtOrd();
                    wordView.setText(this.word);
                });

            } catch (Exception e) {
                System.out.println("Der skete en fejl, mens vi prøvede at hente ord fra Danmarks Radio");
                e.printStackTrace();
            }
        });

        this.images.put(1, R.drawable.forkert1);
        this.images.put(2, R.drawable.forkert2);
        this.images.put(3, R.drawable.forkert3);
        this.images.put(4, R.drawable.forkert4);
        this.images.put(5, R.drawable.forkert5);
        this.images.put(6, R.drawable.forkert6);

    }

    @Override
    public void onClick(View v) {
        if (v == this.guessButton) {
            String guessedWord = guessWordView.getText().toString();

            if (guessedWord.length() > 1) {
                Toast.makeText(this, "Du må kun gætte på et bogstav ad gangen", Toast.LENGTH_LONG).show();
                return;
            }

            this.logik.gætBogstav(guessedWord);
            this.wordView.setText(this.word = this.logik.getSynligtOrd());
            this.guessWordView.setText("");

            if (this.numberOfWrongLetters < this.logik.getAntalForkerteBogstaver()) {
                this.numberOfWrongLetters = this.logik.getAntalForkerteBogstaver();
                this.img.setImageDrawable(getDrawable(this.images.get(this.numberOfWrongLetters)));
            }
        }
    }
}