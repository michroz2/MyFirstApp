package com.example.mich.myfirstapp;

import java.util.concurrent.ThreadLocalRandom;

public class GraphArray {

    int[] iData;

    GraphArray(int range, int minRandom, int maxRandom ) {
        iData = new int[range];
        for (int i = 0; i < iData.length; i++) {
            iData[i] = ThreadLocalRandom.current().nextInt(minRandom, maxRandom + 1);
        }
    }

    public int getData(int i) {
        return iData[i];
    }


    // TODO: 19-Sep-18 написать метод, который переворачивае массив. Первый элемент станет последним, а последним станет первым
}
