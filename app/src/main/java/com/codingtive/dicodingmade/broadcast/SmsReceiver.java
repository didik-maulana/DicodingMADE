package com.codingtive.dicodingmade.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.codingtive.dicodingmade.BuildConfig;

public class SmsReceiver extends BroadcastReceiver {

    final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (Object aPdusObject : pdusObj) {
                        SmsMessage currentMessage = getIncomingMessage(aPdusObject, bundle);
                        String senderNum = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        Log.d(TAG, "sender number " + senderNum + " message " + message);

                        Intent showSmsIntent = new Intent(context, SmsReceiverActivity.class);
                        showSmsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message);
                        context.startActivity(showSmsIntent);
                    }
                }
            } else {
                Log.e(TAG, "onReceive: Bundle is null");
            }
        } catch (Exception error) {
            Log.e(TAG, "Exception sms receiver" + error);
        }
    }

    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSms;

        if (Build.VERSION.SDK_INT >= 23) {
            String format = bundle.getString("format");
            currentSms = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSms = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSms;
    }
}
