package com.example.mich.myfirstapp.dwelling;


public class Tenant {
    private final String name;

    public Tenant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj != null && getName() != null && obj instanceof Tenant) {
            return (getName().equals(((Tenant) obj).getName()));
        }
        return super.equals(obj);
    }
}