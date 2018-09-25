package com.example.mich.myfirstapp.dwelling;

public class PrivateHouse extends Dwelling {
    private int roofColor;

    public PrivateHouse(String address, int constructionYear, int area) {
        super(address, constructionYear, area);
    }

    public void setRoofColor(int roofColor) {

        this.roofColor = roofColor;
    }
}
