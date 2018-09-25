package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;


public class ApartmentTest {

    @Test
    public void moveIn() {
        Apartment apartment = new Apartment("Pajustiku 9", 2000, 123, 2);
        Tenant testMich1 = new Tenant("Mich1");
        Tenant testMich2 = new Tenant("Mich2");
        Tenant testMich3 = new Tenant("Mich3");
        Tenant testMich4 = new Tenant("Mich1");
        Tenant testMich5 = new Tenant("Mich2");
        apartment.moveIn(testMich1);
        apartment.moveIn(testMich2);
        apartment.moveIn(testMich3);
        apartment.moveIn(testMich4);
        apartment.moveIn(testMich5);
        Assert.assertEquals(apartment.getNumTenants(), 3);
        Assert.assertTrue(apartment.contains(testMich3));
    }
}