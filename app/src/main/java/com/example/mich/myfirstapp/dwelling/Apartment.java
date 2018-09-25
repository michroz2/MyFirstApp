package com.example.mich.myfirstapp.dwelling;

/**
 * Apartment - это единичная квартира в многоквартирном доме.
 */
public class Apartment extends Dwelling {


    Apartment(String address, int constructionYear, int area, int flatNumber) {
        super(address, constructionYear, area);
    }
}
