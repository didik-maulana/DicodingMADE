package com.codingtive.dicodingmade.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

import java.lang.ref.WeakReference;

public class AsyncTaskActivity extends AppCompatActivity implements MyAsyncCallback {

    private TextView tvStatus, tvDesc;
    final static String INPUT_STRING = "Learn AsyncTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }

    @Override
    public void onPreExecute() {
        tvStatus.setText(getString(R.string.status_pre));
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String result) {
        tvStatus.setText(getString(R.string.status_post));
        if (result != null) tvDesc.setText(INPUT_STRING);
    }

    private static class DemoAsync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "demo_async";
        WeakReference<MyAsyncCallback> mListener;

        DemoAsync(MyAsyncCallback mListener) {
            this.mListener = new WeakReference<>(mListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "status : onPreExecute");

            MyAsyncCallback mListener = this.mListener.get();
            if (mListener != null) mListener.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(LOG_ASYNC, "status : doInBackground");
            String output = null;
            try {
                String input = params[0];
                output = input + " Happy coding !";
                Thread.sleep(5000);
            } catch (Exception error) {
                Log.e(LOG_ASYNC, error.getMessage());
            }
            return output;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "status : onPostExecute");

            MyAsyncCallback mListener = this.mListener.get();
            if (mListener != null) mListener.onPostExecute(result);
        }
    }
}
