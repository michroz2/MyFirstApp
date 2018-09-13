package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private static String mFileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private static final String TAG = "MainActivity";

    Button btnStop;
    Button btnPlay;
    Button btnRec;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }


    private void startRecording(String mFileName) {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    private void startPlaying(String mFileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        setContentView(R.layout.activity_main);

        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        btnStop = findViewById(R.id.buttonStop);
        btnStop.setOnClickListener(this);

        btnPlay = findViewById(R.id.buttonPlay);
        btnPlay.setOnClickListener(this);

        btnRec = findViewById(R.id.buttonRec);
        btnRec.setOnClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        //TODO: это здесь временно
        stopPlaying();

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPlay:
                Log.d(TAG, "play");
                // TODO: начать воспроизведение последней записи.
                startPlaying(mFileName);
                // TODO: если записи нету, кнопка должна быть неактивна
                // todo: import android.media.MediaPlayer в помощь
                break;
            case R.id.buttonStop:
                Log.d(TAG, "stop");
                // todo: остановить запись
                stopRecording();
                // todo: если запись не воспроизводится и не записывается, то кнопка неактивна

                break;
            case R.id.buttonRec:
                Log.d(TAG, "record");
                // todo: сделать кнопку play неактивной
                // todo: начать воспроизведение
                startRecording(mFileName);
                // todo: android.media.MediaRecorder в помощь
                break;
        }

    }
}

//no commit?