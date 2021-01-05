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
        tv.setText("Det korrekte ord var: " + getIntent().getStringExtra(PlayGameActivity.CORRECT_WORD_IDENTIFIER) + ".");
    }
}