package com.codingtive.dicodingmade.dispatcher;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class DispatcherActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSetScheduler, btnCancelScheduler;
    FirebaseJobDispatcher mDispatcher;

    private String DISPATCHER_TAG = "mydispatcher";
    private String CITY = "Jakarta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        btnSetScheduler = findViewById(R.id.btn_set_scheduler);
        btnCancelScheduler = findViewById(R.id.btn_cancel_scheduler);

        btnSetScheduler.setOnClickListener(this);
        btnCancelScheduler.setOnClickListener(this);

        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_scheduler:
                startDispatcher();
                Toast.makeText(this, "Schedule started", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_cancel_scheduler:
                cancelDispatcher();
                Toast.makeText(this, "Schedule cancel", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void startDispatcher() {
        Bundle mExtrasBundle = new Bundle();
        mExtrasBundle.putString(MyJobService.EXTRAS_CITY, CITY);

        Job myJob = mDispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag(DISPATCHER_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setTrigger(Trigger.executionWindow(0, 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(mExtrasBundle)
                .build();

        mDispatcher.mustSchedule(myJob);
    }

    public void cancelDispatcher() {
        mDispatcher.cancel(DISPATCHER_TAG);
    }
}
