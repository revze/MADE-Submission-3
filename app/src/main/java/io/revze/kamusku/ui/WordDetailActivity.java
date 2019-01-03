package io.revze.kamusku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import io.revze.kamusku.R;

public class WordDetailActivity extends AppCompatActivity {

    public static final String WORD = "word";
    public static final String DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.word_detail_title));
            actionBar.setSubtitle(intent.getStringExtra(WORD));
        }

        TextView tvWord = findViewById(R.id.tv_word);
        TextView tvDescription = findViewById(R.id.tv_description);

        tvWord.setText(intent.getStringExtra(WORD));
        tvDescription.setText(intent.getStringExtra(DESCRIPTION));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
