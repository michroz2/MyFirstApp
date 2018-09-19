package com.example.mich.myfirstapp;

import java.util.Random;


public class GraphArray {

    int[] data;

    GraphArray(int range, int minRandom, int maxRandom ) {
        data = new int[range];
        Random rand = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt((maxRandom-minRandom+1)) + minRandom;
        }
    }

    public int getData(int i) {
        return data[i];
    }


    public void reverse() {
        int left = 0;
        int right = data.length - 1;

        while( left < right ) {
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
    }}
