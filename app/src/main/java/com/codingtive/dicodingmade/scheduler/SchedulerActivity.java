package com.codingtive.dicodingmade.scheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

public class SchedulerActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnStart, btnCancel;
    private int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduler);

        btnStart = findViewById(R.id.btn_start_job);
        btnCancel = findViewById(R.id.btn_stop_job);
        btnStart.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_job:
                startJob();
                break;
            case R.id.btn_stop_job:
                cancelJob();
                break;
        }
    }

    private void startJob(){
        ComponentName mServiceComponent = new ComponentName(this, GetCurrentWeatherJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setRequiresDeviceIdle(false);
        builder.setRequiresCharging(false);

        // 1000 ms = 1 detik
        builder.setPeriodic(18000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }
    private void cancelJob(){
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
        finish();
    }
}
