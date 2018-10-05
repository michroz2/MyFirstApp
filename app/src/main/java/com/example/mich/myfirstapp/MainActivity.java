package com.example.mich.myfirstapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mich.myfirstapp.JukeBox.MediaPlayerStopListener;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    // for permission to RECORD_AUDIO
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    // for permissions to write a data file to external storage
    private static final int DIMENSIONS = 30;
    private static final int REFRESH_TIME = 500;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static String recFileName;
    TextView txtView1;
    ImageView imgViewGraph;
    ImageButton imageButtonStop;
    ImageButton imageButtonPlay;
    ImageButton imageButtonRecord;
    ActivityState activityState;
    Handler timerHandler = new Handler();
    JukeBox jukeBox;
    VolumeGraphComponent graphComponent;
    VolumeTextComponent textComponent;

    Runnable refreshTimer = new Runnable() {
        @Override
        public void run() {
/*
            imgViewGraph.invalidateDrawable(graphComponent);
            imgViewGraph.setImageDrawable(graphComponent);
*/

//TODO: Блин - очень нехорошее место для установки фонта, но в конструкторе и onCreate ругается - типа ещё нет инстанса AssetsManager...
            textComponent.setTypeface(Typeface.createFromAsset(getAssets(), "ssegbi.ttf"));
            imgViewGraph.invalidateDrawable(textComponent);
            imgViewGraph.setImageDrawable(textComponent);

            timerHandler.postDelayed(this, REFRESH_TIME);
        }
    };
    private String[] permissionsRECORD_AUDIO = {Manifest.permission.RECORD_AUDIO};
    private boolean permissionToRecordAccepted;


    public MainActivity() {
        graphComponent = new VolumeGraphComponent(DIMENSIONS);
        textComponent = new VolumeTextComponent();

        jukeBox = new JukeBox();
        jukeBox.setMediaPlayerStopListener(new MediaPlayerStopListener() {
            @Override
            public void onStopped() {
                makeStop();
            }
        });

//        graphComponent.link(jukeBox);
        textComponent.link(jukeBox);
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p>
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity = this.
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void startShowVolumeGraph() {
        // Запустить "таймер" для периодлического взятия значений громкости
        timerHandler.postDelayed(refreshTimer, 0);
    }

    private void stopShowVolumeGraph() {
        timerHandler.removeCallbacks(refreshTimer);
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

    private void startRecording(String mFileName) {
        verifyStoragePermissions(this);
        jukeBox.startRecording(mFileName);
        updateState(ActivityState.RECORDING);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");

        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, permissionsRECORD_AUDIO, REQUEST_RECORD_AUDIO_PERMISSION);
        // По каким-то причинам запросить также сразу пермиссии на запись во внешний сторидж не получается: прога вылетает

        // Record to the external directory for visibility
        recFileName = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath() + "/audiorecord.3gp";

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
                startPlaying(recFileName);
                break;

            case R.id.imageButtonStop:
                makeStop();
                break;

            case R.id.imageButtonRec:
                Log.d(TAG, "record");
                startRecording(recFileName);
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