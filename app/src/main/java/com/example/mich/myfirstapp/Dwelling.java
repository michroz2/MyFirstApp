package com.example.mich.myfirstapp;

import android.location.Address;

import java.time.Year;

public class Dwelling {
    public Address address;
    public Year constructionYear;
    public Tenant[] tenants;

    public void MoveIn(Tenant tenant) {

    }

    public void MoveOut(Tenant tenant) {

    }

    public int NumTenants() {
        return tenants.length;
    }
}
