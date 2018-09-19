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


    public static void reverse(int[] data) {
        int left = 0;
        int right = data.length - 1;

        while( left < right ) {
            // swap the values at the left and right indices
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            // move the left and right index pointers in toward the center
            left++;
            right--;
        }
    }
}
