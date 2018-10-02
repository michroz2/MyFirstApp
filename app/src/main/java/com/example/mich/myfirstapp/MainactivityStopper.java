package com.example.mich.myfirstapp;

public class MainactivityStopper implements ShowStopper {
    MainActivity mainActivity;

    MainactivityStopper(MainActivity object) {
        mainActivity = object;
    }

    public void makeStop() {
        mainActivity.makeStop();
    }

}
