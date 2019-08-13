package com.codingtive.dicodingmade.sqlite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.codingtive.dicodingmade.R;
import com.codingtive.dicodingmade.sqlite.db.NoteHelper;
import com.codingtive.dicodingmade.sqlite.entity.Note;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.codingtive.dicodingmade.sqlite.NoteAddUpdateActivity.REQUEST_ADD;
import static com.codingtive.dicodingmade.sqlite.NoteAddUpdateActivity.REQUEST_UPDATE;
import static com.codingtive.dicodingmade.sqlite.NoteAddUpdateActivity.RESULT_ADD;

public class NoteActivity extends AppCompatActivity implements View.OnClickListener, LoadNotesCallback, ItemNoteCallback {
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private RecyclerView rvNotes;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;
    private ArrayList<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Notes");
        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);
        noteHelper = NoteHelper.getInstance(getApplicationContext());
        noteHelper.open();
        progressBar = findViewById(R.id.progressbar);
        fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);
        adapter = new NoteAdapter(this, this);
        rvNotes.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadNotesAsync(noteHelper, this).execute();
        } else {
            noteList = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (noteList != null) {
                adapter.setNoteList(noteList);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == REQUEST_ADD) {
                if (resultCode == RESULT_ADD) {
                    Note note = data.getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);
                    adapter.addItem(note);
                    rvNotes.smoothScrollToPosition(adapter.getItemCount() - 1);
                    showSnackbarMessage("Satu item berhasil ditambahkan");
                }
            } else if (requestCode == REQUEST_UPDATE) {
                if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                    Note note = data.getParcelableExtra(NoteAddUpdateActivity.EXTRA_NOTE);
                    int position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);
                    adapter.updateItem(position, note);
                    rvNotes.smoothScrollToPosition(position);
                    showSnackbarMessage("Satu item berhasil diubah");
                } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    int position = data.getIntExtra(NoteAddUpdateActivity.EXTRA_POSITION, 0);
                    adapter.removeItem(position);
                    showSnackbarMessage("Satu item berhasil dihapus");
                }
            }
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        noteHelper.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getNoteList());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add) {
            Intent intent = new Intent(NoteActivity.this, NoteAddUpdateActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        }
    }

    @Override
    public void preExecute() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(ArrayList<Note> notes) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setNoteList(notes);
    }

    @Override
    public void onClickNote(int position) {
        Intent intent = new Intent(NoteActivity.this, NoteAddUpdateActivity.class);
        intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position);
        intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, adapter.getNoteList().get(position));
        startActivityForResult(intent, REQUEST_UPDATE);
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Note>> {
        private final WeakReference<NoteHelper> weakNoteHelper;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(NoteHelper noteHelper, LoadNotesCallback callback) {
            weakNoteHelper = new WeakReference<>(noteHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            return weakNoteHelper.get().getAllNotes();
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }
}
