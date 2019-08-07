package com.codingtive.dicodingmade.broadcast;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class DownloadService extends IntentService {

    public static final String TAG = "download_service";
    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Download service running");
        if (intent != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }

            Intent notifyFinishIntent = new Intent(BroadcastActivity.ACTION_DOWNLOAD_STATUS);
            sendBroadcast(notifyFinishIntent);
        }
    }
}
