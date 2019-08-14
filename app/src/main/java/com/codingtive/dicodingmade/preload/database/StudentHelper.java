package com.codingtive.dicodingmade.preload.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.codingtive.dicodingmade.preload.ui.Student;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.codingtive.dicodingmade.preload.database.DatabaseContract.StudentColumns.NAME;
import static com.codingtive.dicodingmade.preload.database.DatabaseContract.StudentColumns.NIM;
import static com.codingtive.dicodingmade.preload.database.DatabaseContract.TABLE_NAME;

public class StudentHelper {
    private static StudentHelper INSTANCE;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public StudentHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static StudentHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StudentHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) database.close();
    }

    public void beginTransaction() {
        database.beginTransaction();
    }

    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    public void endTransaction() {
        database.endTransaction();
    }

    public ArrayList<Student> getAllData() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Student> arrayList = new ArrayList<>();
        Student student;
        if (cursor.getCount() > 0) {
            do {
                student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                student.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                arrayList.add(student);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Student> getDataByName(String name) {
        Cursor cursor = database.query(TABLE_NAME, null, NAME + " LIKE ?", new String[]{name}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<Student> arrayList = new ArrayList<>();
        Student student;
        if (cursor.getCount() > 0) {
            do {
                student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                student.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                student.setNim(cursor.getString(cursor.getColumnIndexOrThrow(NIM)));
                arrayList.add(student);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }


    public long insert(Student student) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(NAME, student.getName());
        initialValues.put(NIM, student.getNim());
        return database.insert(TABLE_NAME, null, initialValues);
    }

    public void insertTransaction(Student student) {
        String sql = "INSERT INTO " + TABLE_NAME + " (" + NAME + ", " + NIM
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, student.getName());
        stmt.bindString(2, student.getNim());
        stmt.execute();
        stmt.clearBindings();
    }
}
