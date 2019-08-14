package com.codingtive.dicodingmade.preload.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingtive.dicodingmade.R;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> students = new ArrayList<>();

    public void setData(ArrayList<Student> students) {
        if (students.size() > 0) {
            this.students.clear();
        }
        this.students.addAll(students);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder viewHolder, int position) {
        viewHolder.bindStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNim;
        private TextView textViewNama;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNim = itemView.findViewById(R.id.txt_nim);
            textViewNama = itemView.findViewById(R.id.txt_nama);
        }

        void bindStudent(Student student) {
            textViewNim.setText(student.getNim());
            textViewNama.setText(student.getName());
        }
    }
}
