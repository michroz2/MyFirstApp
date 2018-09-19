package com.example.mich.myfirstapp;

import java.util.Random;

import static com.example.mich.myfirstapp.GraphArray.swap;


public class GraphArray {

    public int[] data;

    GraphArray(int range, int minRandom, int maxRandom) {
        data = new int[range];
        Random rand = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt((maxRandom - minRandom + 1)) + minRandom;
        }
    }

    public int getData(int i) {
        return data[i];
    }


    public static void reverse(int[] data) {
// Переворачивает любой одноместный массив с ног на голову
        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            int temp = data[left];
            data[left] = data[right];
            data[right] = temp;

            left++;
            right--;
        }
    }

    static void swap(int[] data, int i, int j) { //меняет местами два элемента массива
        int temp = 0;
        temp = data[i];
        data[i] = data[j];
        data[j] = temp;

    }

    public static void michsort(int[] data, boolean asc) {
//Тестовый метод для изучения циклов и тренировки навыков программирования.
// Находим минимальное и максимальное значения массива и разносим их по краям, а потом идём к центру

        int left = 0;
        int right = data.length - 1;
        int curmin = data[left];
        int curmax = data[right];
        int curminindex = left;
        int curmaxindex = right;

        while (left < right) {
            curmin = data[left];
            curmax = data[right];
            curminindex = left;
            curmaxindex = right;
            if (curmin > curmax) {
                swap(data, curminindex, curmaxindex);
                curmin = data[left];
                curmax = data[right];
            }
            for (int i = (left + 1); i < right; i++) {
                if (data[i] < curmin) {
                    curmin = data[i];
                    curminindex = i;
                } else if (data[i] > curmax) {
                    curmax = data[i];
                    curmaxindex = i;
                }
            }
            if (curminindex != left) swap(data, left, curminindex);
            if (curmaxindex != right) swap(data, right, curmaxindex);

            left++;
            right--;
        }
        if (!asc) { // значит наоборот :)
            reverse(data);
        }

    }
}