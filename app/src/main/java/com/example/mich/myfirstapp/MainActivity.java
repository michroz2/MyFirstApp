package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.annotation.AnyRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private static String mFileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static boolean isRecording = false;
    private static boolean isPlaying = false;
    private static final String TAG = "MainActivity";

    private static String strMessage = "START RECORDING";
    TextView txtViewMessage = null;
    Button btnStop;
    Button btnPlay;
    Button btnRec;
    ImageButton ibtnStop;
    ImageButton ibtnPlay;
    ImageButton ibtnRec;

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
        isRecording = true;
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        isRecording = false;

    }
    private void startPlaying(String mFileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            isPlaying = true;
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying(); // и освобождает медиаплейер
                    // активизировать нужные кнопки
                    btnPlay.setEnabled(true);
                    btnStop.setEnabled(false);
                    btnRec.setEnabled(true);

                    ibtnPlay.setEnabled(true);
                    ibtnStop.setEnabled(false);
                    ibtnRec.setEnabled(true);

                    // установить текст в центре
                    strMessage = "PLAY OR RECORD";
                    txtViewMessage.setText(strMessage);

                }
            });

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        isPlaying = false;

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


        ibtnStop = findViewById(R.id.imageButtonStop);
        ibtnStop.setOnClickListener(this);

        ibtnPlay = findViewById(R.id.imageButtonPlay);
        ibtnPlay.setOnClickListener(this);

        ibtnRec = findViewById(R.id.imageButtonRec);
        ibtnRec.setOnClickListener(this);

        //Установить начальное состояние кнопок (потому что в ресурсах не срабатывает SELECTOR для ImageButton)
        btnPlay.setEnabled(false);
        btnStop.setEnabled(false);
        btnRec.setEnabled(true);

        ibtnPlay.setEnabled(false);
        ibtnStop.setEnabled(false);
        ibtnRec.setEnabled(true);

        //Установить текст
        txtViewMessage = findViewById(R.id.textView);
        txtViewMessage.setText(strMessage);

    }

    @Override
    public void onStop() {
        super.onStop();

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
            case R.id.imageButtonPlay:
                Log.d(TAG, "play");
                // начать воспроизведение последней записи.
                startPlaying(mFileName);
                // установить кнопки:
                btnPlay.setEnabled(false);
                btnStop.setEnabled(true);
                btnRec.setEnabled(false);

                ibtnPlay.setEnabled(false);
                ibtnStop.setEnabled(true);
                ibtnRec.setEnabled(false);

                // установить текст
                strMessage = "PLAYING...";
                txtViewMessage.setText(strMessage);


                break;
            case R.id.buttonStop:
            case R.id.imageButtonStop:
                Log.d(TAG, "stop");
                //остановить запись или воспроизведение
                if( isRecording) {
                    stopRecording();
                }
                else {
                    if (isPlaying) {
                        stopPlaying();
                    }
                }
                // активизировать нужные кнопки
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);
                btnRec.setEnabled(true);

                ibtnPlay.setEnabled(true);
                ibtnStop.setEnabled(false);
                ibtnRec.setEnabled(true);

                // установить текст в центре
                strMessage = "PLAY OR RECORD";
                txtViewMessage.setText(strMessage);
                break;
            case R.id.buttonRec:
            case R.id.imageButtonRec:
                Log.d(TAG, "record");
                //сделать кнопку play, rec неактивной, а stop активной
                btnPlay.setEnabled(false); //на всякий случай
                btnStop.setEnabled(true);
                btnRec.setEnabled(false);

                ibtnPlay.setEnabled(false); //на всякий случай
                ibtnStop.setEnabled(true);
                ibtnRec.setEnabled(false);
                // начать запись
                startRecording(mFileName);
                // установить текст в центре
                strMessage = "RECORDING...";
                txtViewMessage.setText(strMessage);
                break;
        }

    }
}

//no commit?