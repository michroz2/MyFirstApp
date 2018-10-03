package com.example.mich.myfirstapp;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import com.example.mich.myfirstapp.VolumeGraphComponent.ValueReceiver;

import java.io.IOException;

public class JukeBox implements ValueReceiver {
    private static final String TAG = "JukeBox";

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private DataFileComponent dataFileComponent;
    private String dataFileName;

    private MediaPlayerStopListener playerStopListener;

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
        // Output file for Volume data
        // Интересно куда теперь напишут этот файл и можно ли его достать будет?..
//        dataFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecord.csv";
        dataFileName = "/sdcard/Temp/audiorecord.csv";
        dataFileComponent.startRecording(dataFileName);


        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(mFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare mediaRecorder failed");
        }

        mediaRecorder.start();
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
        dataFileComponent.write(result);
        return result;
    }

    public interface MediaPlayerStopListener {
        void onStopped();
    }
}
