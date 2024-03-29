package com.codingtive.dicodingmade.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {
    TextView tvOnceDate, tvOnceTime, tvRepeatingTime;
    EditText edtOnceMessage, edtRepeatingMessage;
    ImageButton btnOnceDate, btnOnceTime, btnRepeatingTime;
    Button btnSetOnce, btnSetRepeating, btnCancelAlarm;

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    final String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        bindView();
        alarmReceiver = new AlarmReceiver();

    }

    private void bindView() {
        tvOnceDate = findViewById(R.id.tv_once_date);
        btnOnceDate = findViewById(R.id.btn_once_date);
        tvOnceTime = findViewById(R.id.tv_once_time);
        btnOnceTime = findViewById(R.id.btn_once_time);
        edtOnceMessage = findViewById(R.id.edt_once_message);
        btnSetOnce = findViewById(R.id.btn_set_once_alarm);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        edtRepeatingMessage = findViewById(R.id.edt_repeating_message);
        btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        btnSetRepeating = findViewById(R.id.btn_set_repeating_alarm);
        btnCancelAlarm = findViewById(R.id.btn_cancel_alarm);

        btnOnceDate.setOnClickListener(this);
        btnOnceTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);
        btnSetRepeating.setOnClickListener(this);
        btnCancelAlarm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_once_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.btn_once_time:
                TimePickerFragment timePickerFragmentOne = new TimePickerFragment();
                timePickerFragmentOne.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
                break;
            case R.id.btn_set_once_alarm:
                String onceDate = tvOnceDate.getText().toString();
                String onceTime = tvOnceTime.getText().toString();
                String onceMessage = edtOnceMessage.getText().toString();

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                        onceDate,
                        onceTime,
                        onceMessage);
                break;
            case R.id.btn_repeating_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
                break;
            case R.id.btn_set_repeating_alarm:
                String repeatTime = tvRepeatingTime.getText().toString();
                String repeatMessage = edtRepeatingMessage.getText().toString();
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                        repeatTime, repeatMessage);
                break;
            case R.id.btn_cancel_alarm:
                alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
                break;
        }
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        tvOnceDate.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        switch (tag) {
            case TIME_PICKER_ONCE_TAG:
                tvOnceTime.setText(dateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(dateFormat.format(calendar.getTime()));
            default:
                break;
        }
    }
}
