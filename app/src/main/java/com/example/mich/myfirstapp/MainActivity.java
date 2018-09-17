package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private static String mFileName = null;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    /*
        private static boolean isRecording = false;
        private static boolean isPlaying = false;
    */
    private static final String TAG = "MainActivity";

    private String strMessage = getString(R.string.state_initial); //"START RECORDING";
    TextView txtViewMessage = null;
    Button btnStop;
    Button btnPlay;
    Button btnRec;
    ImageButton ibtnStop;
    ImageButton ibtnPlay;
    ImageButton ibtnRec;
    ActivityState mActivityState = ActivityState.INITIAL;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

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
        updateState(ActivityState.RECORDING);
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        updateState(ActivityState.STOPPED);
    }

    private void startPlaying(String mFileName) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
            updateState(ActivityState.PLAYING);
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                    //TODO check if we need this here:
                    updateState(ActivityState.STOPPED);
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
            updateState(ActivityState.INITIAL);
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        updateState(ActivityState.STOPPED);
    }

    //Тестовая процедура для вывода данных максимальной громкости медиарекордера (по UserInteraction, например) во время рекординга
    private void showRecAmplitude(MediaRecorder mRecorder) {
        if (mActivityState == ActivityState.RECORDING) {
            Integer intRecAmplitude = mRecorder.getMaxAmplitude();
            String sRecAmplitude = intRecAmplitude.toString();
            Log.d(TAG, "Громкость = " + sRecAmplitude);
            // установить текст Амплитуды в центре экрана
            txtViewMessage.setText(sRecAmplitude);

        }

    }

    private void updateState(ActivityState state) {

        mActivityState = state;

        txtViewMessage.setText(getString(state.strId));

        btnPlay.setEnabled(state.playBtn);
        btnStop.setEnabled(state.stopBtn);
        btnRec.setEnabled(state.recBtn);

        ibtnPlay.setEnabled(state.playBtn);
        ibtnStop.setEnabled(state.stopBtn);
        ibtnRec.setEnabled(state.recBtn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";


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


        updateState(ActivityState.INITIAL); //наверное это можно было и в скобках сверху сделать. но, возможно, не помешает лишний раз установить текущий стэйт

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "on stop");

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
        Log.d(TAG, "on click");
        switch (view.getId()) {
            case R.id.buttonPlay:
            case R.id.imageButtonPlay:
                Log.d(TAG, "play");
                // начать воспроизведение последней записи.
                startPlaying(mFileName);
                break;

            case R.id.buttonStop:
            case R.id.imageButtonStop:
                Log.d(TAG, "stop");
                //остановить либо запись либо воспроизведение - что там сейчас идёт
                switch (mActivityState) {
                    case RECORDING:
                        stopRecording();
                        //TODO остановить таймер
                        break;
                    case PLAYING:
                        stopPlaying();
                        break;
                }
                break;

            case R.id.buttonRec:
            case R.id.imageButtonRec:
                Log.d(TAG, "record");
                startRecording(mFileName);
                //TODO - запустить таймер и начать анализировать громкость
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "on BackPressed");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "on Destroy");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.d(TAG, "on ContentChanged");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "on Pause");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "on Restart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "on Resume");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        //       Log.d(TAG, "on UserInteraction");
        showRecAmplitude(mRecorder);
    }


}

//no commit?