package com.example.mich.myfirstapp.dwelling;

import java.util.Objects;

public class Tenant {
    private final String name;

    // Хотелось бы, конечно, что-то вроде такого:
    // public Person name;
    // Понятно, что идёт процесс обучения, но хотелось бы изучать и использовать, то, что есть полезного (если это вообще есть??) в Андроиде:
    // - объект Человека с уже вероятно написанными для него разными методами, подозреваю, что есть...
    // цель -  учиться пользоваться имеющимися данными и методами, например взять/добавить человека из телефонных Контактов.

    // Конструктор
    Tenant(String n) {
        name = n;
    }

    // Хотелось бы иметь метод для сравнения if (tenant1 == tenant2)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(name, tenant.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}