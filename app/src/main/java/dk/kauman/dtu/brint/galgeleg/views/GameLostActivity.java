package dk.kauman.dtu.brint.galgeleg.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import dk.kauman.dtu.brint.galgeleg.R;

public class GameLostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lost);

        TextView tv = findViewById(R.id.correctWordText);
        tv.setText("The correct word was: " + getIntent().getStringExtra(PlayGameActivity.CORRECT_WORD_IDENTIFIER) + ".");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }
}