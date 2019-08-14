package com.codingtive.dicodingmade.preload.service;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.codingtive.dicodingmade.R;
import com.codingtive.dicodingmade.preload.database.StudentHelper;
import com.codingtive.dicodingmade.preload.ui.Student;
import com.codingtive.dicodingmade.preload.ui.StudentPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class LoadDataAsync extends AsyncTask<Void, Integer, Boolean> {
    private final String TAG = LoadDataAsync.class.getSimpleName();
    private StudentHelper studentHelper;
    private StudentPreference prefs;
    private WeakReference<LoadDataCallback> weakCallback;
    private WeakReference<Resources> weakResources;

    LoadDataAsync(
            StudentHelper studentHelper,
            StudentPreference prefs,
            LoadDataCallback callback,
            Resources resources) {
        this.studentHelper = studentHelper;
        this.prefs = prefs;
        this.weakCallback = new WeakReference<>(callback);
        this.weakResources = new WeakReference<>(resources);
    }

    private ArrayList<Student> preLoadRaw() {
        ArrayList<Student> students = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = weakResources.get();
            InputStream rawDict = res.openRawResource(R.raw.data_mahasiswa);
            reader = new BufferedReader(new InputStreamReader(rawDict));
            do {
                line = reader.readLine();
                String[] splitString = line.split("\t");
                Student student;

                student = new Student(splitString[0], splitString[1]);
                students.add(student);

            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute");
        weakCallback.get().onPreload();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        weakCallback.get().onProgressUpdate(values[0]);
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean firstRun = prefs.getFirstRun();
        if (firstRun) {
            ArrayList<Student> students = preLoadRaw();

            studentHelper.open();
            double progress = 30;
            publishProgress((int) progress);
            Double progressMaxInsert = 80.0;
            Double progressDiff = (progressMaxInsert - progress) / students.size();

            boolean isInsertSuccess;
            try {
                studentHelper.beginTransaction();
                for (Student student : students) {
                    if (isCancelled()) {
                        break;
                    } else {
                        studentHelper.insertTransaction(student);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                }
                if (isCancelled()) {
                    isInsertSuccess = false;
                    prefs.setFirstRun(false);
                    weakCallback.get().onLoadCancel();
                } else {
                    studentHelper.setTransactionSuccess();
                    isInsertSuccess = true;
                    prefs.setFirstRun(false);
                }
            } catch (Exception e) {
                Log.e(TAG, "doInBackground: Exception");
                isInsertSuccess = false;
            } finally {
                studentHelper.endTransaction();
            }
            studentHelper.close();
            publishProgress((int) progress);

            return isInsertSuccess;
        } else {
            try {
                synchronized (this) {
                    this.wait(2000);
                    publishProgress(50);

                    this.wait(2000);
                    double maxProgress = 100;
                    publishProgress((int) maxProgress);

                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) weakCallback.get().onLoadSuccess();
        else weakCallback.get().onLoadFailed();
    }
}
