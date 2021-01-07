package dk.kauman.dtu.brint.galgeleg.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dk.kauman.dtu.brint.galgeleg.R;

public class SelectWordActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String WORD_LIST_IDENTIFIER = "wordlist";
    public static final String WORD_IDENTIFIER = "word";
    private ArrayList<String> words = new ArrayList<>();
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.words = getIntent().getStringArrayListExtra(WORD_LIST_IDENTIFIER);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_select_word, R.id.selectWordTextView, words) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv = view.findViewById(R.id.selectWordTextView);
                tv.setText(words.get(position));

                return view;
            }
        };

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView text = new TextView(this);
        text.setText("VÆLG ORD");
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setPadding(0, 10, 0, 10);
        layout.addView(text);

        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        layout.addView(listView);

        setContentView(layout);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intent.putExtra(WORD_IDENTIFIER, words.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}