package com.codingtive.dicodingmade.intent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class IntentActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMoveActivity;
    Button btnMoveActivityData;
    Button btnMoveActivityObject;
    Button btnIntentImplicit;
    Button btnIntentResult;
    TextView tvIntentResult;

    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnMoveActivityData = findViewById(R.id.btn_move_activity_data);
        btnMoveActivityObject = findViewById(R.id.btn_move_activity_object);
        btnIntentImplicit = findViewById(R.id.btn_move_implicit);
        btnIntentResult = findViewById(R.id.btn_move_for_result);
        tvIntentResult = findViewById(R.id.tv_result);

        btnMoveActivity.setOnClickListener(this);
        btnMoveActivityData.setOnClickListener(this);
        btnMoveActivityObject.setOnClickListener(this);
        btnIntentImplicit.setOnClickListener(this);
        btnIntentResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_move_activity:
                Intent intent = new Intent(IntentActivity.this, IntentDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_move_activity_data:
                Intent intentWithData = new Intent(IntentActivity.this, IntentDetailDataActivity.class);
                intentWithData.putExtra(IntentDetailDataActivity.EXTRA_NAME, "Didik");
                intentWithData.putExtra(IntentDetailDataActivity.EXTRA_HOBBIES, "Programming");
                startActivity(intentWithData);
                break;
            case R.id.btn_move_activity_object:
                Person person = new Person();
                person.setName("Didik");
                person.setAge(19);
                person.setEmail("didikmaulana49@gmail.com");
                person.setCity("Banjarnegara");

                Intent intentWithObject = new Intent(IntentActivity.this, IntentDetailObjectActivity.class);
                intentWithObject.putExtra(IntentDetailObjectActivity.EXTRA_PERSON, person);
                startActivity(intentWithObject);
                break;
            case R.id.btn_move_implicit:
                String phoneNumber = "085602683812";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(dialPhoneIntent);
                break;
            case R.id.btn_move_for_result:
                Intent intentResult = new Intent(IntentActivity.this, IntentResultActivity.class);
                startActivityForResult(intentResult, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == IntentResultActivity.RESULT_CODE) {
                int selectedValue = data != null ? data.getIntExtra(IntentResultActivity.EXTRA_SELECTED_VALUE, 0) : 0;
                tvIntentResult.setText(String.format("Result : %s", selectedValue));
            }
        }
    }
}
