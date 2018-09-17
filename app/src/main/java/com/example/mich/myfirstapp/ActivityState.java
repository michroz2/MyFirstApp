package com.example.mich.myfirstapp;

public enum ActivityState {
    INITIAL(R.string.state_initial, false, false, true),
    PLAYING(R.string.state_playing, false, true, false ),
    RECORDING(R.string.state_recording, false, true, false),
    STOPPED(R.string.state_stopped, true, false, true);

    public final int strId;
    public final boolean playBtn;
    public final boolean stopBtn;
    public final boolean recBtn;

    ActivityState(int strId, boolean playBtn, boolean stopBtn, boolean recBtn) {
        this.strId = strId;
        this.playBtn = playBtn;
        this.stopBtn = stopBtn;
        this.recBtn = recBtn;
    }
}
