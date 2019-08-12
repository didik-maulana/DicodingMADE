package com.codingtive.dicodingmade.sharedpref;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codingtive.dicodingmade.R;

public class PrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        getSupportFragmentManager().beginTransaction().add(R.id.setting_holder, new MyPreferenceFragment()).commit();
    }
}
