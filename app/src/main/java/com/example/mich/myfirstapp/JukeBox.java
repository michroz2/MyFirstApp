package com.example.mich.myfirstapp;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class JukeBox implements ValueReceiver {
    private static final String TAG = "JukeBox";
    private static JukeBox instance;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private ShowStopper showStopper;

    JukeBox(ShowStopper showStopper) {
        this.showStopper = showStopper;
    }

    public void startRecording(String mFileName) {
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
        mediaRecorder.stop();
        releaseMediaDevices();
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
//TODO - Как переключить ActivityState в положение STOPPED?

        } catch (IOException e) {
            Log.e(TAG, "mediaPlayer failed");
            stopPlaying();
        }
    }

    public void stopPlaying() {
        mediaPlayer.stop();
        releaseMediaDevices();
        showStopper.makeStop();
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
        return mediaRecorder == null ? 0 : mediaRecorder.getMaxAmplitude();
    }
}
