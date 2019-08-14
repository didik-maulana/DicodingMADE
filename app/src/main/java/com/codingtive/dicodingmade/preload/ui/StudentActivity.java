package com.codingtive.dicodingmade.preload.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;
import com.codingtive.dicodingmade.preload.database.StudentHelper;
import com.codingtive.dicodingmade.preload.service.DataManagerService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.codingtive.dicodingmade.preload.service.DataManagerService.ACTIVITY_HANDLER;
import static com.codingtive.dicodingmade.preload.service.DataManagerService.CANCEL_MESSAGE;
import static com.codingtive.dicodingmade.preload.service.DataManagerService.FAILED_MESSAGE;
import static com.codingtive.dicodingmade.preload.service.DataManagerService.PREPARATION_MESSAGE;
import static com.codingtive.dicodingmade.preload.service.DataManagerService.SUCCESS_MESSAGE;
import static com.codingtive.dicodingmade.preload.service.DataManagerService.UPDATE_MESSAGE;

interface HandlerCallback {
    void preparation();

    void updateProgress(long progress);

    void loadSuccess();

    void loadFailed();

    void loadCancel();
}

public class StudentActivity extends AppCompatActivity implements HandlerCallback {
    RecyclerView recyclerView;
    StudentAdapter studentAdapter;
    StudentHelper studentHelper;
    ProgressBar progressBar;
    TextView loadingTextView;
    Messenger mActivityMessenger;

    Messenger mBoundService;
    boolean mServiceBound;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBoundService = new Messenger(service);
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        studentHelper = new StudentHelper(this);
        studentAdapter = new StudentAdapter();

        loadingTextView = findViewById(R.id.loading);
        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);

        Intent mBoundServiceIntent = new Intent(StudentActivity.this, DataManagerService.class);
        mActivityMessenger = new Messenger(new IncomingHandler(this));
        mBoundServiceIntent.putExtra(ACTIVITY_HANDLER, mActivityMessenger);
        bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private void handleLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            loadingTextView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            loadingTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void preparation() {
        handleLoading(true);
        Toast.makeText(this, "GET STARTED FETCH DATA", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateProgress(long progress) {
        Log.d("PROGRESS", "updateProgress : " + progress);
        String loadingText = "Loading " + progress;
        loadingTextView.setText(loadingText);
        progressBar.setProgress((int) progress);
    }

    @Override
    public void loadSuccess() {
        studentHelper.open();
        ArrayList<Student> students = studentHelper.getAllData();
        studentHelper.close();

        studentAdapter.setData(students);
        Toast.makeText(this, "FINISHED", Toast.LENGTH_LONG).show();
        handleLoading(false);
    }

    @Override
    public void loadFailed() {
        Toast.makeText(this, "FINISHED", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadCancel() {
        handleLoading(false);
        Toast.makeText(this, "CANCEL", Toast.LENGTH_LONG).show();
    }

    private static class IncomingHandler extends Handler {
        WeakReference<HandlerCallback> weakCallback;

        IncomingHandler(HandlerCallback callback) {
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PREPARATION_MESSAGE:
                    weakCallback.get().preparation();
                    break;
                case UPDATE_MESSAGE:
                    Bundle bundle = msg.getData();
                    long progress = bundle.getLong("KEY_PROGRESS");
                    weakCallback.get().updateProgress(progress);
                    break;
                case SUCCESS_MESSAGE:
                    weakCallback.get().loadSuccess();
                    break;
                case FAILED_MESSAGE:
                    weakCallback.get().loadFailed();
                    break;
                case CANCEL_MESSAGE:
                    weakCallback.get().loadCancel();
                    break;
            }
        }
    }
}
