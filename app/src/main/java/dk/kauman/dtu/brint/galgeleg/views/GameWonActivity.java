package dk.kauman.dtu.brint.galgeleg.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import dk.kauman.dtu.brint.galgeleg.R;

public class GameWonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_won);

        TextView tv = findViewById(R.id.numberOfGuessesText);
        tv.setText("Du brugte " + getIntent().getIntExtra(PlayGameActivity.NUMBER_OF_GUESSES_IDENTIFIER, 0) + " forsøg.");

        LottieAnimationView animation = findViewById(R.id.iv_winning_badge);
        animation.playAnimation();
    }
}