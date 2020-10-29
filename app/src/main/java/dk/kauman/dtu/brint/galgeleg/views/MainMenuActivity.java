package dk.kauman.dtu.brint.galgeleg.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import dk.kauman.dtu.brint.galgeleg.R;

public class MainMenuActivity extends Activity implements View.OnClickListener {

    private static final int ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.goToGameButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == v.getRootView().findViewById(R.id.goToGameButton)) {
            Intent intent = new Intent(this, PlayGameActivity.class);
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_REQUEST_CODE) {
            int numberOfGuesses = data.getIntExtra(PlayGameActivity.RETURN_VALUE_IDENTIFIER, 0);

            // TODO Implement saving results
        }
    }
}