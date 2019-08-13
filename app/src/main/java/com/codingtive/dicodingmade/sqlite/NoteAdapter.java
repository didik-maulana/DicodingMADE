package com.codingtive.dicodingmade.sqlite;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;
import com.codingtive.dicodingmade.sqlite.entity.Note;

import java.util.ArrayList;

interface ItemNoteCallback {
    void onClickNote(int position);
}

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private ArrayList<Note> noteList = new ArrayList<>();
    private Activity activity;
    private ItemNoteCallback itemNoteCallback;

    public NoteAdapter(Activity activity, ItemNoteCallback itemNoteCallback) {
        this.activity = activity;
        this.itemNoteCallback = itemNoteCallback;
    }

    public ArrayList<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        if (noteList.size() > 0) {
            this.noteList.clear();
        }
        this.noteList.addAll(noteList);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        this.noteList.add(note);
        notifyItemInserted(noteList.size() - 1);
    }

    public void updateItem(int position, Note note) {
        this.noteList.set(position, note);
        notifyItemChanged(position, note);
    }

    public void removeItem(int position) {
        this.noteList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, noteList.size());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        return new NoteViewHolder(view, itemNoteCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, final int position) {
        noteViewHolder.bindNote(noteList.get(position));
        noteViewHolder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemNoteCallback.onClickNote(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final TextView tvTitle, tvDescription, tvDate;
        final CardView cvNote;

        public NoteViewHolder(@NonNull View itemView, final ItemNoteCallback itemNoteCallback) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_title);
            tvDescription = itemView.findViewById(R.id.tv_item_description);
            tvDate = itemView.findViewById(R.id.tv_item_date);
            cvNote = itemView.findViewById(R.id.cv_item_note);
        }

        void bindNote(Note note) {
            tvTitle.setText(note.getTitle());
            tvDate.setText(note.getDate());
            tvDescription.setText(note.getDescription());
        }
    }
}
