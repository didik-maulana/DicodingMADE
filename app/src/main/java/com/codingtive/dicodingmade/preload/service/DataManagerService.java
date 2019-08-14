package com.codingtive.dicodingmade.preload.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.codingtive.dicodingmade.preload.database.StudentHelper;
import com.codingtive.dicodingmade.preload.ui.StudentPreference;

public class DataManagerService extends Service {
    public static final int PREPARATION_MESSAGE = 0;
    public static final int UPDATE_MESSAGE = 1;
    public static final int SUCCESS_MESSAGE = 2;
    public static final int FAILED_MESSAGE = 3;
    public static final int CANCEL_MESSAGE = 4;
    public static final String ACTIVITY_HANDLER = "activity_handler";

    private String TAG = DataManagerService.class.getSimpleName();
    private LoadDataAsync loadData;
    private Messenger mActivityMessenger;
    LoadDataCallback callback = new LoadDataCallback() {
        @Override
        public void onPreload() {
            Message message = Message.obtain(null, PREPARATION_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProgressUpdate(long progress) {
            try {
                Message message = Message.obtain(null, UPDATE_MESSAGE);
                Bundle bundle = new Bundle();
                bundle.putLong("KEY_PROGRESS", progress);
                message.setData(bundle);
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadSuccess() {
            Message message = Message.obtain(null, SUCCESS_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadFailed() {
            Message message = Message.obtain(null, FAILED_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadCancel() {
            Message message = Message.obtain(null, CANCEL_MESSAGE);
            try {
                mActivityMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    public DataManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        StudentHelper studentHelper = StudentHelper.getInstance(getApplicationContext());
        StudentPreference prefs = new StudentPreference(getApplicationContext());

        loadData = new LoadDataAsync(studentHelper, prefs, callback, getResources());

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loadData.cancel(true);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        mActivityMessenger = intent.getParcelableExtra(ACTIVITY_HANDLER);
        loadData.execute();
        return mActivityMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        loadData.cancel(true);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "reBind: ");
        super.onRebind(intent);
    }
}
