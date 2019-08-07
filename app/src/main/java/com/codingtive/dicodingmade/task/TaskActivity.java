package com.codingtive.dicodingmade.task;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codingtive.dicodingmade.R;

import java.lang.ref.WeakReference;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener, AsyncCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Button btnToDetail = findViewById(R.id.btn_open_detail);
        btnToDetail.setOnClickListener(this);

        DelayAsync delayAsync = new DelayAsync(this);
        delayAsync.execute();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_open_detail) {
            Intent detailIntent = new Intent(TaskActivity.this, TaskDetailActivity.class);
            detailIntent.putExtra(TaskDetailActivity.EXTRA_TITLE, "Halo, good news");
            detailIntent.putExtra(TaskDetailActivity.EXTRA_MESSAGE, "Now you can learn Android Expert");
            startActivity(detailIntent);
        }
    }

    @Override
    public void postAsync() {
        showNotification(TaskActivity.this, "Hi, how are you ?", "Lets hangout with me", 110);
    }

    private static class DelayAsync extends AsyncTask<Void, Void, Void> {
        WeakReference<AsyncCallback> callback;
        DelayAsync(AsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch(InterruptedException error) {
                error.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.get().postAsync();
        }
    }

    private void showNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "Navigation channel";
        Intent notifDetailIntent = new Intent(this, TaskDetailActivity.class);
        notifDetailIntent.putExtra(TaskDetailActivity.EXTRA_TITLE, title);
        notifDetailIntent.putExtra(TaskDetailActivity.EXTRA_MESSAGE, message);

        PendingIntent pendingIntent = TaskStackBuilder.create(this)
                .addParentStack(TaskDetailActivity.class)
                .addNextIntent(notifDetailIntent)
                .getPendingIntent(110, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }
}

interface AsyncCallback {
    void postAsync();
}
