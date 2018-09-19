package com.example.mich.myfirstapp;

import java.util.concurrent.ThreadLocalRandom;

public class GraphArray {

    int[] iData = new int[100];

    GraphArray() { // todo: в конструктор передать размер и диапазон

        int min = -100;
        int max = 100;

        for (int i = 0; i < iData.length; i++) {
            iData[i] = ThreadLocalRandom.current().nextInt(min, max + 1);
//https://stackoverflow.com/questions/363681/how-to-generate-random-integers-within-a-specific-range-in-java
        }
    }

    public int getData(int i) {
        return iData[i];
    }
}
