package com.codingtive.dicodingmade.preload.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class StudentPreference {
    private static final String PREF_NAME = "student_pref";
    private static final String APP_FIRST_RUN = "app_first_run";
    private SharedPreferences prefs;

    public StudentPreference(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean(APP_FIRST_RUN, true);
    }

    public void setFirstRun(Boolean state) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(APP_FIRST_RUN, state);
        editor.apply();
    }
}
