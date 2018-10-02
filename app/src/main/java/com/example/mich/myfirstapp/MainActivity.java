package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private static final int DIMENSIONS = 200;

    private static final int REFRESH_TIME = 2000;

    private static String fileName;

    TextView txtView1;

    ImageView imgViewGraph;

    ImageButton imageButtonStop;
    ImageButton imageButtonPlay;
    ImageButton imageButtonRecord;

    ActivityState activityState;

    Handler timerHandler = new Handler();

    JukeBox jukeBox;
    MainactivityStopper mainactivityStopper;

    VolumeGraphComponent graphComponent;

    Runnable refreshTimer = new Runnable() {
        @Override
        public void run() {
            imgViewGraph.invalidateDrawable(graphComponent);
            imgViewGraph.setImageDrawable(graphComponent);
            timerHandler.postDelayed(this, REFRESH_TIME);
        }
    };

    // for permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};


    public MainActivity() {
        graphComponent = new VolumeGraphComponent();
        mainactivityStopper = new MainactivityStopper(this);
        jukeBox = new JukeBox(mainactivityStopper);

        graphComponent.link(jukeBox);

    }

    private void startShowVolumeGraph() {
        // Запустить "таймер" для периодлического взятия значений громкости
        timerHandler.postDelayed(refreshTimer, 0);
    }

    private void stopShowVolumeGraph() {
        timerHandler.removeCallbacks(refreshTimer);
    }

    private void showRecAmplitude(MediaRecorder recorder) {
        if (activityState == ActivityState.RECORDING) {
            int amplitude = recorder.getMaxAmplitude();
//            Log.d(TAG, "Громкость = " + amplitude);
//            // определить максимальное значение для настройки вывода графа:
//            if (amplitude > maxAmplitude) {
//                maxAmplitude = amplitude;
//                mRecAmplitudeGraph.setMaxValue(amplitude);
//            }
//
//            // вставить значение амплитуды в массив на текущее место:
//            volumeArray[currentVolumeArrayPosition] = amplitude;
//            // передвинуть "текущее место" для следующей записи:
//            currentVolumeArrayPosition++;
//            currentVolumeArrayPosition %= DIMENSIONS;
//
//            // Передать массив, заставить перерисовать и показать массив соответствующий громкости:
//            mRecAmplitudeGraph.setDrawArray(volumeArray);
//            imgViewGraph.invalidateDrawable(mRecAmplitudeGraph);
//            imgViewGraph.setImageDrawable(mRecAmplitudeGraph);

            // показать текст Амплитуды в текстовом окне
            txtView1.setText("Vol: " + amplitude);

        }
    }

// Конец Блока для вывода Максимальной Амплитуды звука при записи

    private void startRecording(String mFileName) {
        jukeBox.startRecording(mFileName);
        updateState(ActivityState.RECORDING);
    }

    private void stopRecording() {
        jukeBox.stopRecording();
        updateState(ActivityState.STOPPED);
    }

    private void startPlaying(String name) {
        jukeBox.startPlaying(name);
        updateState(ActivityState.PLAYING);
    }

    private void stopPlaying() {
        jukeBox.stopPlaying();
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

        txtView1 = findViewById(R.id.textView);

        imgViewGraph = findViewById(R.id.imageView);


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

        txtView1.setText(getString(newState.strId));


        imageButtonPlay.setEnabled(newState.playBtn);
        imageButtonStop.setEnabled(newState.stopBtn);
        imageButtonRecord.setEnabled(newState.recBtn);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "on Pause");
        //recomended just in case
        timerHandler.removeCallbacks(refreshTimer);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "on stop");

        jukeBox.releaseMediaDevices();


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(TAG, "on UserInteraction");

        //
        //  showRecAmplitude(mediaRecorder); // если конечно при этом идёт запись, иначе ничего не выйдет


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

        switch (view.getId()) {
            case R.id.imageButtonPlay:
                Log.d(TAG, "play");
                // начать воспроизведение последней записи.
                startPlaying(fileName);
                break;

            case R.id.imageButtonStop:
                makeStop();
                break;

            case R.id.imageButtonRec:
                Log.d(TAG, "record");
                startRecording(fileName);
                startShowVolumeGraph();
                break;
        }
    }


    public void makeStop() {
        Log.d(TAG, "stop");
        //остановить либо запись либо воспроизведение - смотря что там сейчас идёт
        switch (activityState) {
            case RECORDING:
                stopShowVolumeGraph();
                stopRecording();
                break;
            case PLAYING:
                stopPlaying();
                break;
        }
    }
}