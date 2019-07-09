package com.codingtive.dicodingmade.espresso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class UiTestActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTitle;
    TextView tvTitle;
    Button btnChange, btnMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Learn Espresso");
        }

        edtTitle = findViewById(R.id.edt_title);
        tvTitle = findViewById(R.id.tv_title);
        btnChange = findViewById(R.id.btn_change);
        btnMove = findViewById(R.id.btn_move);

        btnChange.setOnClickListener(this);
        btnMove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                edtTitle.setText("Lalala");
                String title = edtTitle.getText().toString().trim();
                tvTitle.setText(title);
                break;
            case R.id.btn_move:
                String input = edtTitle.getText().toString().trim();
                Intent intent = new Intent(this, UiTestDetailActivity.class);
                intent.putExtra(UiTestDetailActivity.EXTRA_TITLE, input);
                startActivity(intent);
                break;
        }
    }
}
