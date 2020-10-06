package dk.kauman.dtu.brint.galgeleg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewById(R.id.goToGameButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == v.getRootView().findViewById(R.id.goToGameButton)) {
            Intent intent = new Intent(this, PlayGame.class);
            startActivity(intent);
        }
    }
}