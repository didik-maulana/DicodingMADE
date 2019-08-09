package com.codingtive.dicodingmade.readwrite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class ReadWriteActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write);

        btnNew = findViewById(R.id.button_new);
        btnOpen = findViewById(R.id.button_open);
        btnSave = findViewById(R.id.button_save);
        editContent = findViewById(R.id.edt_file);
        editTitle = findViewById(R.id.edt_title);

        btnSave.setOnClickListener(this);
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);

        path = getFilesDir();
    }

    private void newFile() {
        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clear file", Toast.LENGTH_LONG).show();
    }

    private void loadData(String title) {
        FileModel fileModel = FileHelper.readFromFile(this, title);
        editTitle.setText(fileModel.getFilename());
        editContent.setText(fileModel.getData());
        Toast.makeText(this, "Loading " + fileModel.getFilename() + " data", Toast.LENGTH_SHORT).show();
    }

    private void openFile() {
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, path.list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose file");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else if (editContent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileModel fileModel = new FileModel();
            fileModel.setFilename(title);
            fileModel.setData(text);
            FileHelper.writeToFile(fileModel, this);
            Toast.makeText(this, "Saving " + fileModel.getFilename() + " file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }
}
