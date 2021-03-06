package dk.kauman.dtu.brint.galgeleg.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import dk.kauman.dtu.brint.galgeleg.R;

public class MainMenuActivity extends Activity implements View.OnClickListener {

    private static final int ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.goToGameButton).setOnClickListener(this);
        findViewById(R.id.highScoreListButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == v.getRootView().findViewById(R.id.goToGameButton)) {
            Intent intent = new Intent(this, PlayGameActivity.class);
            startActivity(intent);
        } else if (v == v.getRootView().findViewById(R.id.highScoreListButton)) {
            Intent intent = new Intent(this, HighScoreListActivity.class);
            startActivity(intent);
        }
    }
}