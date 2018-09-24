package com.example.mich.myfirstapp.dwelling;

public class Apartment extends Dwelling {
    private final int numFlats;
    private final int[] flats;

    public Apartment(String address, int constructionYear, int area, int numFlats) {
        super(address, constructionYear, area);
        this.numFlats = numFlats;
        flats = new int[numFlats];
        for (int i : flats) {
            flats[i] = i + 1;

        }
    }

    public int getNumFlats() {
        return numFlats;
    }

}
