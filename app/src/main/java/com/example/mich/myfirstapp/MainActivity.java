package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
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


    /**
     * 1) TODO: Написать класс "Желец" у него есть имя
     * 2) TODO: написать класс "Желище". У него есть адресс, жилая площадь и год постройкти. туда можно заселять "Желец"
     * 3) TODO: написать класс "Квартирный дом", который наследует "Желище". есть поля "Квартира"
     * 4) TODO: написать класс "Частный дом"
     * 5) TODO: написать сортировщик жилищь. а) по площади, b) по году застройки, c) по кол-ву жильцов
     */


    private static final String TAG = "MainActivity";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private static String fileName;

    TextView txtViewMessage;

    Button buttonStop;

    Button buttonPlay;

    Button buttonRecord;

    ImageButton imageButtonStop;

    ImageButton imageButtonPlay;

    ImageButton imageButtonRecord;

    ActivityState activityState;

    Handler timerHandler = new Handler();

    private MediaRecorder mediaRecorder;

    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            showRecAmplitude(mediaRecorder);
            timerHandler.postDelayed(this, 500);
        }
    };
    private MediaPlayer mediaPlayer;
    // for permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted;

    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    private void startShowVolume() {
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopShowVolume() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void startRecording(String mFileName) {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        mediaRecorder.start();
        updateState(ActivityState.RECORDING);
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        updateState(ActivityState.STOPPED);
    }

    private void startPlaying(String mFileName) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mFileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
            updateState(ActivityState.PLAYING);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
            stopPlaying();
        }
    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
        updateState(ActivityState.STOPPED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        // Record to the external cache directory for visibility
        fileName = getExternalCacheDir().getAbsolutePath() + "/audiorecordtest.3gp";

        txtViewMessage = findViewById(R.id.textView);

        buttonStop = findViewById(R.id.buttonStop);
        buttonStop.setOnClickListener(this);

        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        buttonRecord = findViewById(R.id.buttonRec);
        buttonRecord.setOnClickListener(this);


        imageButtonStop = findViewById(R.id.imageButtonStop);
        imageButtonStop.setOnClickListener(this);

        imageButtonPlay = findViewById(R.id.imageButtonPlay);
        imageButtonPlay.setOnClickListener(this);

        imageButtonRecord = findViewById(R.id.imageButtonRec);
        imageButtonRecord.setOnClickListener(this);

        updateState(ActivityState.INITIAL);

    }

    private void updateState(ActivityState newState) {
        Log.d(TAG, "updateState: " + activityState + " -> " + newState);
        if (newState == activityState) {
            Log.d(TAG, "nothing to do. the new newState is the same");
            return;
        }

        activityState = newState;

        txtViewMessage.setText(getString(newState.strId));

        buttonPlay.setEnabled(newState.playBtn);
        buttonStop.setEnabled(newState.stopBtn);
        buttonRecord.setEnabled(newState.recBtn);

        imageButtonPlay.setEnabled(newState.playBtn);
        imageButtonStop.setEnabled(newState.stopBtn);
        imageButtonRecord.setEnabled(newState.recBtn);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "on Pause");
        //recomended just in case
        timerHandler.removeCallbacks(timerRunnable);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "on stop");

        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(TAG, "on UserInteraction");

        //
        showRecAmplitude(mediaRecorder); // если конечно идёт запись, иначе ничего не выйдет

        //Вывод массива по юзерскому, например, клику на поле окна
        //txtViewMessage.setText(indexGraphArray.toString() + ": " + String.valueOf(mTestArray.getData(indexGraphArray++ % 100)));

        //test сортировки michSort
        //      TestArray someArray = new TestArray(8, 0, 100);
        //      someArray.sort(true);
        //      someArray.sort(false);


        int[] arr = new int[]{1, 2, 3, 4, 7, 5};
        printIntArray(arr, "массив перед сортировкой");
        TestArray.michSort(arr, false);
        printIntArray(arr, "массив после сортировки");

    }

    private void showRecAmplitude(MediaRecorder recorder) {
        if (activityState == ActivityState.RECORDING) {
            int amplitude = recorder.getMaxAmplitude();
            Log.d(TAG, "Громкость = " + amplitude);
            // показать текст Амплитуды
            txtViewMessage.setText("Vol: " + amplitude);
        }
    }

    private void printIntArray(int[] arr, String comment) {
        Log.d(TAG, "\n=============================");
        Log.d(TAG, comment);
        Log.d(TAG, "=============================");
        printIntArray(arr);
        Log.d(TAG, "\n");
    }

    private void printIntArray(int[] arr) {
        for (int a : arr) {
            Log.d(TAG, String.valueOf(a));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) {
            finish();
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
                startPlaying(fileName);
                break;

            case R.id.buttonStop:
            case R.id.imageButtonStop:
                Log.d(TAG, "stop");
                //остановить либо запись либо воспроизведение - что там сейчас идёт
                switch (activityState) {
                    case RECORDING:
                        stopShowVolume();
                        stopRecording();
                        break;
                    case PLAYING:
                        stopPlaying();
                        break;
                }
                break;

            case R.id.buttonRec:
            case R.id.imageButtonRec:
                Log.d(TAG, "record");
                startRecording(fileName);
                startShowVolume();
                break;
        }
    }
}