package com.example.mich.myfirstapp.dwelling;

public class Tenant {
    private final String name;

    // Хотелось бы, конечно, что-то вроде такого:
    // public Person name;
    // Понятно, что идёт процесс обучения, но хотелось бы изучать и использовать, то, что есть полезного (если это вообще есть??) в Андроиде:
    // - объект Человека с уже вероятно написанными для него разными методами, подозреваю, что есть...
    // цель -  учиться пользоваться имеющимися данными и методами, например взять/добавить человека из телефонных Контактов.

    public Tenant(String n) {
        name = n;
    }
}