package com.example.mich.myfirstapp;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.example.mich.myfirstapp.VolumeGraphComponent.ValueReceiver;

import java.io.IOException;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class JukeBox implements ValueReceiver {
    private static final String TAG = "JukeBox";

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private DataFileComponent dataFileComponent;
    private String dataFileName;

    private ScreamCommandComponent screamCommandComponent;

    private MediaPlayerStopListener playerStopListener;
    private ScreamCommandHandler screamCommandHandler;

    public void setScreamCommandHandler(ScreamCommandHandler screamCommandHandler) {
        this.screamCommandHandler = screamCommandHandler;
    }

    public void stopPlaying() {
        if (mediaPlayer != null) {

            mediaPlayer.stop();
            releaseMediaDevices();
            if (playerStopListener != null) {
                playerStopListener.onStopped();
            }
        }
    }


    public void startRecording(String mFileName) {

        dataFileComponent = new DataFileComponent();
        // Output file for Volume data - to Downloads directory
        dataFileName = Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS).getAbsolutePath() + "/audiorecord.csv";
        dataFileComponent.startRecording(dataFileName);

        screamCommandComponent = new ScreamCommandComponent();

        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare mediaRecorder failed");
        }

    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            releaseMediaDevices();
        }
        dataFileComponent.stopRecording();
    }

    public void startPlaying(String mFileName) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(mFileName);
            mediaPlayer.prepare();
            mediaPlayer.start();

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });

        } catch (IOException e) {
            Log.e(TAG, "mediaPlayer failed");
            stopPlaying();
        }
    }

    public void setMediaPlayerStopListener(MediaPlayerStopListener listener) {
        playerStopListener = listener;
    }

    public void releaseMediaDevices() {
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
    public int getValue() {
        int result = (mediaRecorder == null) ? 0 : mediaRecorder.getMaxAmplitude();
        dataFileComponent.write(result); // This is for Excel analyses only. should be removed afterwards.
        screamCommandComponent.nextValue(result);
        if (screamCommandComponent.isCommandDetected()) {
            screamCommandHandler.onScreamCommand();
        }
        return result;
    }

    public interface ScreamCommandHandler {
        void onScreamCommand();
    }


    public interface MediaPlayerStopListener {
        void onStopped();
    }
}
