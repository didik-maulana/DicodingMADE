package com.codingtive.dicodingmade.localization;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class LocalizationActivity extends AppCompatActivity {

    TextView tvHello, tvPlural, tvXliff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);

        tvHello = findViewById(R.id.tv_hello);
        tvPlural = findViewById(R.id.tv_plural);
        tvXliff = findViewById(R.id.tv_xliff);

        String hello = String.format(
                getResources().getString(R.string.hello_world),
                "Didik Maulana A",
                3,
                "Zidni Ridwan");
        tvHello.setText(hello);

        String pluralText = String.format(getResources()
                .getQuantityString(R.plurals.numberOfSongsAvailable, 12, 4));
        tvPlural.setText(pluralText);

        tvXliff.setText(getResources().getString(R.string.app_homeurl));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_localization, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_language_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
