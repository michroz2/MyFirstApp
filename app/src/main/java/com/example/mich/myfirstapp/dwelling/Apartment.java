package com.example.mich.myfirstapp.dwelling;

/**
 * Apartment - это единичная квартира в многоквартирном доме.
 */
public class Apartment extends Dwelling {


    private final int flatNumber;

    Apartment(String address, int constructionYear, int area, int flatNumber) {
        super(address, constructionYear, area);
        this.flatNumber = flatNumber;
    }

    @Override
    public String getAddress() {
        return super.getAddress() + "#" + flatNumber;
    }
}
