package com.example.mich.myfirstapp.dwelling;

/**
 * Apartment - это единичная квартира в многоквартирном доме.
 */
public class Apartment extends Dwelling {
    private final ApartmentHouse house;
    private final int flatNum;

    static String delim = ", app#: ";

    public Apartment( ApartmentHouse house, int flatNum, int flatArea) {
        super(house.getAddress() + delim + String.valueOf(flatNum), house.getConstructionYear(), flatArea);
        this.house = house;
        this.flatNum = flatNum;
    }

}
