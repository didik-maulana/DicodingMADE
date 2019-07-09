package com.codingtive.dicodingmade.espresso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class UiTestDetailActivity extends AppCompatActivity {

    TextView tvDetailTitle;
    public static final String EXTRA_TITLE =  "extra_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Detail Activity");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvDetailTitle = findViewById(R.id.tv_detail_title);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        tvDetailTitle.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
