package com.codingtive.dicodingmade.broadcast;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnPermission, btnDownload;

    final int SMS_REQUEST_CODE = 101;
    private BroadcastReceiver downloadReceiver;
    public static final String ACTION_DOWNLOAD_STATUS = "download_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        btnPermission = findViewById(R.id.btn_permission);
        btnDownload = findViewById(R.id.btn_download);

        btnPermission.setOnClickListener(this);
        btnDownload.setOnClickListener(this);

        receiveDownload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downloadReceiver != null) {
            unregisterReceiver(downloadReceiver);
        }
    }

    private void receiveDownload() {
        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(DownloadService.TAG, "Download finished");
                Toast.makeText(context, "Download finished", Toast.LENGTH_SHORT).show();
            }
        };

        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(downloadReceiver, downloadIntentFilter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission) {
            PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE);
        } else if (v.getId() == R.id.btn_download) {
            Intent downloadServiceIntent = new Intent(this, DownloadService.class);
            startService(downloadServiceIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Sms receiver granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sms receiver denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
