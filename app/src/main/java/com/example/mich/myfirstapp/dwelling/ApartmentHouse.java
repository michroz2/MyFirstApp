package com.example.mich.myfirstapp.dwelling;

/**
 * Это отдельный класс для многоквартирного дома
 */
public class ApartmentHouse {
    private String address;
    private final int constructionYear;
    private Tenant[] tenants; // Непонятно нужно ли это тут вообще, в этом виде, или двойной массив?
    private final int totalarea;
    private final int numFlats;

    ApartmentHouse(String address, int constructionYear, int totalarea, int numFlats) {
        this.address = address;
        this.constructionYear = constructionYear;
        this.totalarea = totalarea;
        this.numFlats = numFlats;
    }

    public String getAddress() {
        return address;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public int getTotalarea() {
        return totalarea;
    }

    public int getNumFlats() {
        return numFlats;
    }
}
