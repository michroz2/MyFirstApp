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

    private JukeBox() {

    }

    static JukeBox getJukeBox() {
        if (instance == null) {
            instance = new JukeBox();
        }
        return instance;
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
            Log.e(TAG, "prepare() failed");
        }

        mediaRecorder.start();
    }

    public void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
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
            Log.e(TAG, "prepare() failed");
            stopPlaying();
        }
    }


    public void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void release() {
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
