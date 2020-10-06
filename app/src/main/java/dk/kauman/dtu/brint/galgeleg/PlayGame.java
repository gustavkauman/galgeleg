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

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlayGame extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private String word;
    private TextView wordView;
    private Galgelogik logik;
    private TextView guessWordView;
    private Button guessButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        this.img = findViewById(R.id.hangManImage);
        this.wordView = findViewById(R.id.secretWordText);
        this.guessWordView = findViewById(R.id.nextLetterInput);
        this.guessButton = findViewById(R.id.guessButton);
        this.logik = new Galgelogik();

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
        }
    }
}