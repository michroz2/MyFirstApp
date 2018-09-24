package com.example.mich.myfirstapp.dwelling;


public abstract class Dwelling {
    private String address;
    private int constructionYear;
    private Tenant[] tenants;

    public void MoveIn(Tenant tenant) {

    }

    public void MoveOut(Tenant tenant) {

    }

    public int NumTenants() {
        return tenants.length;
    }
}
