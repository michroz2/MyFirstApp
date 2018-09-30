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
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int DIMENSIONS = 200;
    private static final int MS_DELAY = 20;
    private static String fileName;
    TextView txtView1;
    ImageView imgView1;
    Button buttonStop;
    Button buttonPlay;
    Button buttonRecord;
    ImageButton imageButtonStop;
    ImageButton imageButtonPlay;
    ImageButton imageButtonRecord;
    ActivityState activityState;
    Handler timerHandler = new Handler();
    int[] volumeArray = new int[DIMENSIONS];
    int currentVolumeArrayPosition = 0;
    int maxAmplitude = 0;

    // Блок для вывода Максимальной Амплитуды звука при записи :
    RecAmplitudeGraph mRecAmplitudeGraph = new RecAmplitudeGraph();
    private MediaRecorder mediaRecorder;
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            showRecAmplitude(mediaRecorder);
            timerHandler.postDelayed(this, MS_DELAY);
        }
    };
    // for permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private MediaPlayer mediaPlayer;

    private void startShowRecAmplitude() {
// Обнулить массив значений громкости
        for (int i = 0; i < DIMENSIONS; i++) {
            volumeArray[i] = 0;
        }
        // Запустить "таймер" для периодлического взятия значений громкости
        timerHandler.postDelayed(timerRunnable, 0);
    }

    private void stopShowRecAmplitude() {
        timerHandler.removeCallbacks(timerRunnable);
    }

    private void showRecAmplitude(MediaRecorder recorder) {
        if (activityState == ActivityState.RECORDING) {
            int amplitude = recorder.getMaxAmplitude();
            Log.d(TAG, "Громкость = " + amplitude);
            // определить максимальное значение для настройки вывода графа:
            if (amplitude > maxAmplitude) {
                maxAmplitude = amplitude;
                mRecAmplitudeGraph.setMaxValue(amplitude);
            }

            // вставить значение амплитуды в массив на текущее место:
            volumeArray[currentVolumeArrayPosition] = amplitude;
            currentVolumeArrayPosition++;
            currentVolumeArrayPosition %= DIMENSIONS;
            // вывести массив значений громкости в графическое окно
            showGraphArray();

            // показать текст Амплитуды в текстовом окне
//            txtView1.setText("Vol: " + amplitude);

        }
    }

    private void showGraphArray() {
        // Передать массив, заставить перерисовать и показать массив линий соответствующих громкости, начиная с текущего элемента:
        mRecAmplitudeGraph.setDrawArray(volumeArray);
        imgView1.invalidateDrawable(mRecAmplitudeGraph);
        imgView1.setImageDrawable(mRecAmplitudeGraph);
    }
// Конец Блока для вывода Максимальной Амплитуды звука при записи

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

        txtView1 = findViewById(R.id.textView);

        imgView1 = findViewById(R.id.imageView);

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

        txtView1.setText(getString(newState.strId));

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
        showRecAmplitude(mediaRecorder); // если конечно при этом идёт запись, иначе ничего не выйдет

        //Вывод массива по юзерскому, например, клику на поле окна
        //txtView1.setText(indexGraphArray.toString() + ": " + String.valueOf(mTestArray.getData(indexGraphArray++ % 100)));

        //test сортировки michSort
        //      TestArray someArray = new TestArray(8, 0, 100);
        //      someArray.sort(true);
        //      someArray.sort(false);


/*
        int[] arr = new int[]{1, 2, 3, 4, 7, 5};
        logPrintIntArray(arr, "массив перед сортировкой");
        TestArray.michSort(arr, false);
        logPrintIntArray(arr, "массив после сортировки");

*/
        // Тест для imageView


    }

    private void logPrintIntArray(int[] arr, String comment) {
        Log.d(TAG, "\n=============================");
        Log.d(TAG, comment);
        Log.d(TAG, "=============================");
        logPrintIntArray(arr);
        Log.d(TAG, "\n");
    }

    private void logPrintIntArray(int[] arr) {
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
                        stopShowRecAmplitude();
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
                startShowRecAmplitude();
                break;
        }
    }
}