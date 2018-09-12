package com.example.mich.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    Button btnStop;
    Button btnPlay;
    Button btnRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        setContentView(R.layout.activity_main);

        btnStop = findViewById(R.id.buttonStop);
        btnStop.setOnClickListener(this);

        btnPlay = findViewById(R.id.buttonPlay);
        btnPlay.setOnClickListener(this);

        btnRec = findViewById(R.id.buttonRec);
        btnRec.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlay:
                Log.d(TAG, "play");
                // TODO: начать воспроизведение последней записи.
                // TODO: если записи нету, кнопка должна быть неактивна
                break;
            case R.id.buttonStop:
                Log.d(TAG, "stop");
                // todo: остановить запись
                // todo: если запись не воспроизводится и не записывается, то кнопка неактивна

                break;
            case R.id.buttonRec:
                Log.d(TAG, "record");
                // todo: сделать кнопку play неактивной
                // todo: начать воспроизведение
                break;
        }

    }
}

//no commit?