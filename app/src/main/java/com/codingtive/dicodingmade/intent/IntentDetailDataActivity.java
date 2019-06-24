package com.codingtive.dicodingmade.intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class IntentDetailDataActivity extends AppCompatActivity {

    TextView tvDataReceived;

    public static final String EXTRA_NAME = "extra_name";
    public static final String EXTRA_HOBBIES = "extra_hobbies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_detail_data);

        tvDataReceived = findViewById(R.id.tv_data_received);

        String name = getIntent().getStringExtra(EXTRA_NAME);
        String hobbies = getIntent().getStringExtra(EXTRA_HOBBIES);

        String text = "My name is " + name + ", and my hobby is " + hobbies;
        tvDataReceived.setText(text);
    }
}
