package com.example.mich.myfirstapp;

import java.security.InvalidParameterException;
import java.util.Random;


public class TestArray {

    private int[] data;

    TestArray(int range, int minRandom, int maxRandom) {
        data = new int[range];
        Random rand = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt((maxRandom - minRandom + 1)) + minRandom;
        }
    }

    /**
     * возвращает элемент массива
     *
     * @param i индекс элемента, который нужно получить
     * @return значение i-ого элемента
     */
    @SuppressWarnings("unused")
    public int getData(int i) {
        return data[i];
    }

    /**
     * Переворачивает любой одноместный массив с ног на голову
     *
     * @param data массив для переворачивания
     */
    private static void reverse(int[] data) {
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

    /**
     * Меняет местами два элемента массива
     *
     * @param data массив у которого нужно поменять местами элементы
     * @param i    индекс элемента массива, который нужно поставить на позицию <code>j</code>
     * @param j    индекс элемента массива, который нужно поставить на позицию <code>i</code>
     */
    private static void swap(int[] data, int i, int j) {
        int temp;
        temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Тестовый метод для изучения циклов и тренировки навыков программирования.
     * Находим минимальное и максимальное значения массива и разносим их по краям, а потом идём к центру
     *
     * @param data массив, который нужно отсортировать
     * @param asc  порядок сортировки. <code>true</code> - по возрастанию, <code>false</code> - по убыванию
     */
    public static void michSort(int[] data, boolean asc) {
        if (data == null) {
            throw new InvalidParameterException("data can't be null");
        }

        int left = 0;
        int right = data.length - 1;
        int curmin;
        int curmax;
        int curminindex;
        int curmaxindex;

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

        // todo: порефакторить так, чтобы не нужно было реверсить
        if (!asc) { // значит наоборот :)
            reverse(data); // понятно, что пострадает производительность  - лишние операции.
        }
    }

    /**
     * sort data array
     *
     * @param asc sort order
     */
    public void sort(boolean asc) {
        michSort(data, asc);
    }
}