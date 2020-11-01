package dk.kauman.dtu.brint.galgeleg.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import dk.kauman.dtu.brint.galgeleg.R;
import dk.kauman.dtu.brint.galgeleg.models.GameResult;

public class HighScoreListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        ArrayList<GameResult> gameResults = gson.fromJson(prefs.getString("games", ""), new TypeToken<ArrayList<GameResult>>(){}.getType());

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_high_score_list, R.id.listContentView, gameResults) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view =  super.getView(position, convertView, parent);
                GameResult gr = gameResults.get(position);
                System.out.println(gr);

                TextView dateView = view.findViewById(R.id.listDateElement);
                TextView contentView = view.findViewById(R.id.listContentView);

                dateView.setText(gr.getDate().toLocaleString());
                contentView.setText("Word: " + gr.getWord() + ". Number of guesses: " + gr.getNumberOfGuesses());

                return view;
            }
        };

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);

        setContentView(listView);
    }
}