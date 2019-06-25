package com.codingtive.dicodingmade.intent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.codingtive.dicodingmade.R;

public class IntentResultActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnChoose;
    RadioGroup rgNumbers;

    public static String EXTRA_SELECTED_VALUE = "extra_selected_value";
    public static int RESULT_CODE = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_result);

        btnChoose = findViewById(R.id.btn_choose);
        rgNumbers = findViewById(R.id.rg_numbers);

        btnChoose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_choose) {
            if (rgNumbers.getCheckedRadioButtonId() != 0) {
                int value = 0;
                switch (rgNumbers.getCheckedRadioButtonId()) {
                    case R.id.rb_10:
                        value = 10;
                        break;
                    case R.id.rb_20:
                        value = 20;
                        break;
                    case R.id.rb_30:
                        value = 30;
                        break;
                    case R.id.rb_40:
                        value = 40;
                        break;
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_SELECTED_VALUE, value);
                setResult(RESULT_CODE, resultIntent);
                finish();
            }
        }
    }
}
