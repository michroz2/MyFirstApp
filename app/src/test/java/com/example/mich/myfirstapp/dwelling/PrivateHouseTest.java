package com.example.mich.myfirstapp.dwelling;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrivateHouseTest {

    @Test
    public void moveIn() {
        PrivateHouse testPH = new PrivateHouse("Pajustiku 9", 2000, 123);
        Tenant testMich1 = new Tenant("Mich1");
        Tenant testMich2 = new Tenant("Mich2");
        Tenant testMich3 = new Tenant("Mich3");
        Tenant testMich4 = new Tenant("Mich1");
        Tenant testMich5 = new Tenant("Mich2");
        testPH.moveIn(testMich1);
        testPH.moveIn(testMich2);
        testPH.moveIn(testMich3);
        testPH.moveIn(testMich4);
        testPH.moveIn(testMich5);
        Assert.assertEquals(testPH.getNumTenants(), 3);
        Assert.assertEquals(testPH.tenants[2].getName(), "Mich3");
    }
}