package com.codingtive.dicodingmade.sqlite;

import com.codingtive.dicodingmade.sqlite.entity.Note;

import java.util.ArrayList;

public interface LoadNotesCallback {
    void preExecute();

    void postExecute(ArrayList<Note> notes);
}
