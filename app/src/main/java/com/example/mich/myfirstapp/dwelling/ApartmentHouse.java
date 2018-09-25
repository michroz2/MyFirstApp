package com.example.mich.myfirstapp.dwelling;

/**
 * Это отдельный класс для многоквартирного дома
 */
public class ApartmentHouse extends Dwelling {

    private final Apartment[] apartments;

    ApartmentHouse(String address, int constructionYear, int area, int totalFlats) {
        super(address, constructionYear, area);
        apartments = new Apartment[totalFlats];

        for (int i = 0; i < apartments.length; i++) {
            apartments[i] = new Apartment(address, constructionYear, area / totalFlats, i);
        }
    }
}
