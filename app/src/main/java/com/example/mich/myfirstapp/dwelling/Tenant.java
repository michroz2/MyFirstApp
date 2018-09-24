package com.example.mich.myfirstapp.dwelling;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

public class Tenant {
    private final String name;

    public String getName() {
        return name;
    }

    public Tenant(String name) {
        this.name = name;
    }

    /**
     * Метод для сравнения Жильцов на равенство
     *
     * @param o
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(name, tenant.name);
    }

    /**
     * ХЗ - наверное это для сортировки хорошо - сгенерировано Студией
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}