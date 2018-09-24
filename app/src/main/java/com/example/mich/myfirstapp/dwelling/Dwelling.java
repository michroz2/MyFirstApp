package com.example.mich.myfirstapp.dwelling;


public abstract class Dwelling {
    private String address;
    private int constructionYear;
    private Tenant[] tenants;

    public void moveIn(Tenant tenant) {

    }

    public void moveOut(Tenant tenant) {

    }

    public int getNumTenants() {
        return tenants.length;
    }
}
