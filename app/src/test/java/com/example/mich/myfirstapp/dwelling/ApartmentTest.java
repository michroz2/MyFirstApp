package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;


public class ApartmentTest {

    @Test
    public void moveIn() {
        Apartment testAp = new Apartment("Pajustiku 9", 2000, 123, 2);
        Tenant testMich1 = new Tenant("Mich1");
        Tenant testMich2 = new Tenant("Mich2");
        Tenant testMich3 = new Tenant("Mich3");
        Tenant testMich4 = new Tenant("Mich1");
        Tenant testMich5 = new Tenant("Mich2");
        testAp.moveIn(testMich1);
        testAp.moveIn(testMich2);
        testAp.moveIn(testMich3);
        testAp.moveIn(testMich4);
        testAp.moveIn(testMich5);
        Assert.assertEquals(testAp.getNumTenants(), 3);
        Assert.assertTrue(testAp.contains(testMich3));
    }
}