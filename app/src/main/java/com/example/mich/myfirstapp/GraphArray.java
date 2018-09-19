package com.example.mich.myfirstapp;

public class GraphArray {

    // todo: переменная должна быть спрятана от других классов
    public int[] Data = new int[100]; // TODO: поля в java должны начинаться с маленькой буквы. П

    // TODO: 19-Sep-18 инициализацию data перенести в конструктор класса
    // TODO: 19-Sep-18 массив заполнить случайными значениями от -100 до 100

    public void initArray() {
        for (int i = 0; i < Data.length; i++) {
            Data[i] = i;
        }
    }

    public Integer getData(int i) {       // TODO: у тебя массив int, значит и getData должна возвращать int, a  не Integer
        return Data[(i % Data.length)];   // TODO: зачем брать остаток от деления на длину массива? достаточно return data[i];
    }
}
