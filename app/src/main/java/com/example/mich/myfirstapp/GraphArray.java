package com.example.mich.myfirstapp;

public class GraphArray {

    public int[] Data = new int[100];

    public void initArray() {
        for (int i = 0; i < Data.length; i++) {
            Data[i] = i;
        }
    }

    public Integer getData(int i) {
        return Data[(i % Data.length)];
    }
}
