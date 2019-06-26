package com.codingtive.dicodingmade.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

public class BarActivity extends AppCompatActivity implements BarView, View.OnClickListener {

    EditText edtWidth, edtLength, edtHeight;
    Button btnCalculate;
    TextView tvResult;

    public static BarPresenter presenter;
    private static final String KEY_RESULT = "key_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        edtWidth = findViewById(R.id.edt_width);
        edtLength = findViewById(R.id.edt_length);
        edtHeight = findViewById(R.id.edt_height);
        btnCalculate = findViewById(R.id.btn_calculate);
        tvResult = findViewById(R.id.tv_result);

        btnCalculate.setOnClickListener(this);

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString(KEY_RESULT);
            tvResult.setText(result);
        }

        presenter = new BarPresenter(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_RESULT, tvResult.getText().toString());
    }

    @Override
    public void showVolume(BarModel model) {
        tvResult.setText(String.valueOf(model.getVolume()));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_calculate) {
            String inputWidth = edtWidth.getText().toString().trim();
            String inputLength = edtLength.getText().toString().trim();
            String inputHeight = edtHeight.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvalidDouble = false;

            if (TextUtils.isEmpty(inputWidth)) {
                isEmptyFields = true;
                edtWidth.setError("Lebar tidak boleh kosong");
            }

            if (TextUtils.isEmpty(inputLength)) {
                isEmptyFields = true;
                edtWidth.setError("Panjang tidak boleh kosong");
            }

            if (TextUtils.isEmpty(inputHeight)) {
                isEmptyFields = true;
                edtWidth.setError("Tinggi tidak boleh kosong");
            }

            Double width = toDouble(inputWidth);
            Double length = toDouble(inputLength);
            Double height = toDouble(inputHeight);

            if (width == null) {
                isInvalidDouble = true;
                edtWidth.setError("Isian ukuran tidak valid");
            }

            if (length == null) {
                isInvalidDouble = true;
                edtLength.setError("Isian ukuran tidak valid");
            }

            if (height == null) {
                isInvalidDouble = true;
                edtHeight.setError("Isian ukuran tidak valid");
            }

            if (!isEmptyFields && !isInvalidDouble) {
                presenter.calculateVolume(length, width, height);
            }
        }
    }

    Double toDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
