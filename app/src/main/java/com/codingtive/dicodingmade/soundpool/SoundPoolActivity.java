package com.codingtive.dicodingmade.soundpool;

import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codingtive.dicodingmade.R;

public class SoundPoolActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPlay;
    private SoundPool sp;
    private int soundId;
    private boolean spLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_pool);

        btnPlay = findViewById(R.id.btn_soundpool);
        btnPlay.setOnClickListener(this);

        sp = new SoundPool.Builder().setMaxStreams(10).build();

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if (status == 0) {
                    spLoaded = true;
                } else {
                    Toast.makeText(SoundPoolActivity.this, "Gagal play", Toast.LENGTH_SHORT).show();
                }
            }
        });
        soundId = sp.load(this, R.raw.sound, 1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_soundpool) {
            if (spLoaded) {
                sp.play(soundId, 1, 1, 0, 0, 1);
            }
        }
    }
}
