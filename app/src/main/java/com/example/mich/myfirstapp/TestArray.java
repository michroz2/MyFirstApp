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

    public static String implode(String[] stringArray) {

        for (int i = 1; i < stringArray.length; i++) {
            stringArray[0] += stringArray[i];
        }
        return stringArray[0];

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
        if (i == j) return; //nothing to do
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
        int lowerside;
        int higherside;


        while (left < right) {
            if ((data[left] < data[right])^asc) {  // Переставлять местами если ((L < R) AND desc) или ((R > L) AND asc)
                swap(data, left, right);
            } // крайние элементы теперь стоят в нужном порядке возрастания

            if (asc) {
                lowerside = left;
                higherside = right;
            } else  {
                lowerside = right;
                higherside = left;
            }

            curminindex = lowerside;
            curmaxindex = higherside;
            curmin = data[curminindex];
            curmax = data[curmaxindex];

            for (int i = (left + 1); i < right; i++) {
                if (data[i] < curmin) {
                    curmin = data[i];
                    curminindex = i;
                } else if (data[i] > curmax) {
                    curmax = data[i];
                    curmaxindex = i;
                }
            }
            swap(data, lowerside, curminindex);
            swap(data, higherside, curmaxindex);

            left++;
            right--;
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