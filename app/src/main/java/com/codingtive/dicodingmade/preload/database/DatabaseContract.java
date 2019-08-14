package com.codingtive.dicodingmade.preload.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_NAME = "table_students";

    static final class StudentColumns implements BaseColumns {
        static String NAME = "name";
        static String NIM = "nim";
    }
}
